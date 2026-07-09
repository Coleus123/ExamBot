// UserTestDataTrackerTest.java - исправленная версия
package ru.urfu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.urfu.model.UserData;
import ru.urfu.model.UserTestDataTracker;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестирующий методы класса UserTestDataTracker
 */
class UserTestDataTrackerTest {
    private UserTestDataTracker testDataTracker;

    @BeforeEach
    void setUp() {
        testDataTracker = new UserTestDataTracker();
    }

    /**
     * Проверяет метод startTest и получение данных
     */
    @Test
    void startTestAndGetDataTest() throws InterruptedException {
        testDataTracker.startTest("user1", "Математика");
        UserData data = testDataTracker.getUserData("user1");

        assertEquals("Математика", data.getSubject());
        assertEquals(0L, data.getOption());
        assertEquals(0L, data.getNumberOfQuestion());
        assertEquals(0L, data.getRightNumberOfQuestion());
        Thread.sleep(100);
        assertEquals(100, System.currentTimeMillis() - data.getStartTime(), 50);
    }

    /**
     * Проверяет метод selectOption
     */
    @Test
    void selectOptionTest() {
        testDataTracker.startTest("user2", "Информатика");
        testDataTracker.selectOption("user2", 2L);
        assertEquals(2L, testDataTracker.getUserData("user2").getOption());
    }

    /**
     * Проверяет метод moveToNextQuestion
     */
    @Test
    void moveToNextQuestionTest() {
        testDataTracker.startTest("user3", "Информатика");
        testDataTracker.moveToNextQuestion("user3");
        assertEquals(1L, testDataTracker.getUserData("user3").getNumberOfQuestion());
    }

    /**
     * Проверяет метод markAnswer
     */
    @Test
    void markAnswerTest() {
        testDataTracker.startTest("user4", "Информатика");
        testDataTracker.markAnswer("user4", true);
        assertEquals(1L, testDataTracker.getUserData("user4").getRightNumberOfQuestion());
        testDataTracker.markAnswer("user4", false);
        assertEquals(1L, testDataTracker.getUserData("user4").getRightNumberOfQuestion());
    }

    /**
     * Проверяет метод removeUser
     */
    @Test
    void removeUserTest() {
        testDataTracker.startTest("user5", "Информатика");
        assertTrue(testDataTracker.isUserInTest("user5"));
        testDataTracker.removeUser("user5");
        assertFalse(testDataTracker.isUserInTest("user5"));
    }

    /**
     * Проверяет метод getElapsedTime
     */
    @Test
    void getElapsedTimeTest() throws InterruptedException {
        testDataTracker.startTest("user6", "Информатика");
        Thread.sleep(100);
        assertEquals(100, testDataTracker.getElapsedTime("user6"), 50);
    }

    /**
     * Проверяет метод isUserInTest
     */
    @Test
    void isUserInTestTest() {
        testDataTracker.startTest("user7", "Информатика");
        assertTrue(testDataTracker.isUserInTest("user7"));
        assertFalse(testDataTracker.isUserInTest("user8"));
    }
}