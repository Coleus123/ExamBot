// TestStateHandlerTest.java - тест для TestStateHandler
package ru.urfu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса TestStateHandler
 */
class TestStateHandlerTest {
    private SubjectManager subjectManager;
    private UserTestDataTracker tracker;
    private TestStateHandler testStateHandler;

    @TempDir
    static File tempDir;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        File directory = new File(tempDir, "Test");
        directory.mkdirs();

        File subject = new File(directory, "Математика");
        subject.mkdirs();

        File variant = new File(subject, "1");
        variant.mkdirs();

        File answ = new File(variant, "answ");
        answ.mkdirs();
        File ques = new File(variant, "ques");
        ques.mkdirs();

        File firstQues = new File(ques, "1.txt");
        FileWriter writer = new FileWriter(firstQues);
        writer.write("Вопрос");
        writer.close();

        File firstAnsw = new File(answ, "1.txt");
        FileWriter writer2 = new FileWriter(firstAnsw);
        writer2.write("Ответ");
        writer2.close();
    }

    @BeforeEach
    void setUp() {
        subjectManager = new SubjectManager();
        subjectManager.populateData(tempDir.getPath() + "\\Test");
        tracker = new UserTestDataTracker();
        testStateHandler = new TestStateHandler(subjectManager, tracker);
    }

    @Test
    void handleSubjectSelection_CorrectSubject() {
        tracker.startTest("user1", UserData.NO_SUBJECT);
        List<String> result = testStateHandler.handleTestInput("user1", "Математика");
        assertEquals("Введите вариант, всего доступно 1 вариантов", result.get(0));
        assertEquals("Математика", tracker.getUserData("user1").getSubject());
    }

    @Test
    void handleSubjectSelection_WrongSubject() {
        tracker.startTest("user2", UserData.NO_SUBJECT);
        List<String> result = testStateHandler.handleTestInput("user2", "Физика");
        assertEquals("Нет такого предмета. Доступны следующие предметы: Математика/", result.get(0));
        assertEquals(UserData.NO_SUBJECT, tracker.getUserData("user2").getSubject());
    }

    @Test
    void handleOptionSelection_CorrectOption() {
        tracker.startTest("user3", "Математика");
        List<String> result = testStateHandler.handleTestInput("user3", "1");
        assertEquals("Вопрос", result.get(0));
        assertEquals(1L, tracker.getUserData("user3").getOption());
    }

    @Test
    void handleOptionSelection_WrongOption() {
        tracker.startTest("user4", "Математика");
        List<String> result = testStateHandler.handleTestInput("user4", "2");
        assertEquals("Нет такого варианта. Введите снова", result.get(0));
        assertEquals(0L, tracker.getUserData("user4").getOption());
    }

    @Test
    void handleOptionSelection_InvalidFormat() {
        tracker.startTest("user5", "Математика");
        List<String> result = testStateHandler.handleTestInput("user5", "abc");
        assertEquals("Ошибка: некорректный формат числа", result.get(0));
        assertEquals(0L, tracker.getUserData("user5").getOption());
    }

    @Test
    void handleAnswer_Correct() {
        tracker.startTest("user6", "Математика");
        tracker.selectOption("user6", 1L);

        List<String> result = testStateHandler.handleTestInput("user6", "Ответ");
        assertEquals(Constants.RIGHT_ANSWER, result.get(0));
        assertEquals(1L, tracker.getUserData("user6").getRightNumberOfQuestion());
    }

    @Test
    void handleAnswer_Wrong() {
        tracker.startTest("user7", "Математика");
        tracker.selectOption("user7", 1L);

        List<String> result = testStateHandler.handleTestInput("user7", "Не ответ");
        assertEquals(Constants.WRONG_ANSWER, result.get(0));
        assertEquals(0L, tracker.getUserData("user7").getRightNumberOfQuestion());
    }
}