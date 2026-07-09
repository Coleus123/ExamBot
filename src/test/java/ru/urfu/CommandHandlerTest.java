// CommandHandlerTest.java - тест для CommandHandler
package ru.urfu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса CommandHandler
 */
class CommandHandlerTest {
    private SubjectManager subjectManager;
    private UserTestDataTracker tracker;
    private CommandHandler commandHandler;

    @BeforeEach
    void setUp() {
        subjectManager = new SubjectManager();
        tracker = new UserTestDataTracker();
        commandHandler = new CommandHandler(subjectManager, tracker);
    }

    @Test
    void handleStart_WhenUserNotInTest() {
        List<String> result = commandHandler.handleStart("user1");
        assertEquals(List.of(Constants.START), result);
        assertFalse(tracker.isUserInTest("user1"));
    }

    @Test
    void handleStart_WhenUserInTest() {
        tracker.startTest("user2", UserData.NO_SUBJECT);
        assertTrue(tracker.isUserInTest("user2"));

        List<String> result = commandHandler.handleStart("user2");
        assertEquals(List.of(Constants.START), result);
        assertFalse(tracker.isUserInTest("user2"));
    }

    @Test
    void handleHelp() {
        tracker.startTest("user3", UserData.NO_SUBJECT);
        assertTrue(tracker.isUserInTest("user3"));

        List<String> result = commandHandler.handleHelp("user3");
        assertEquals(List.of(Constants.HELP), result);
        assertFalse(tracker.isUserInTest("user3"));
    }

    @Test
    void handleExitTest_WhenUserNotInTest() {
        List<String> result = commandHandler.handleExitTest("user4");
        assertEquals(List.of(Constants.EXIT_TEST_FALSE), result);
    }

    @Test
    void handleExitTest_WhenUserInTest() {
        tracker.startTest("user5", UserData.NO_SUBJECT);
        assertTrue(tracker.isUserInTest("user5"));

        List<String> result = commandHandler.handleExitTest("user5");
        assertEquals(List.of(Constants.EXIT_TEST), result);
        assertFalse(tracker.isUserInTest("user5"));
    }

    @Test
    void handleStartTest() {
        List<String> result = commandHandler.handleStartTest("user6");
        assertEquals("Выберите предмет: ", result.get(0));
        assertTrue(tracker.isUserInTest("user6"));
        assertEquals(UserData.NO_SUBJECT, tracker.getUserData("user6").getSubject());
    }
}