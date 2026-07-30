package ru.urfu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.urfu.model.UserData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDataTest {
    private UserData userData;

    @BeforeEach
    public void setUp() {
        userData = new UserData("Математика", 2L);
    }

    /**
     * Проверяет, правильно ли создается пользователь, проходящий тест
     */
    @Test
    public void userDataConstructorTest() throws InterruptedException {
        assertEquals("Математика", userData.getSubject());
        assertEquals(2L, userData.getOption());
        assertEquals(0L, userData.getNumberOfQuestion());
        assertEquals(0L, userData.getRightNumberOfQuestion());
        Thread.sleep(100);
        assertEquals(100, System.currentTimeMillis() - userData.getStartTime(), 50);
    }

    /**
     * Проверяет, правильно ли присваивается вариант
     */
    @Test
    public void setOptionTest() {
        userData.setOption(3L);
        assertEquals(3L, userData.getOption());
    }

    /**
     * Проверяет, правильно ли инкрементируется номер вопроса
     */
    @Test
    public void incrementNumberOfQuestionTest() {
        userData.incrementNumberOfQuestion();
        assertEquals(1L, userData.getNumberOfQuestion());
    }

    /**
     * Проверяет, правильно ли инкрементируется количество правильных ответов
     */
    @Test
    public void incrementRightNumberOfQuestionTest() {
        userData.incrementRightNumberOfQuestion();
        assertEquals(1L, userData.getRightNumberOfQuestion());
    }

    /**
     * Проверяет определение выбранного предмета
     */
    @Test
    public void isSubjectSelectedTest() {
        assertTrue(userData.isSubjectSelected());
        UserData emptyUser = new UserData(UserData.NO_SUBJECT, 0L);
        assertFalse(emptyUser.isSubjectSelected());
    }
}