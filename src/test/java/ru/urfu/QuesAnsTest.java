package ru.urfu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.urfu.model.QuesAns;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Модульные тесты для класса QuesAns.
 */
public class QuesAnsTest {

    private QuesAns quesAns;

    @BeforeEach
    public void setUp() {
        quesAns = new QuesAns();
        quesAns.AddBasicQuestion();
    }

    /**
     * Проверяет добавление 10 основных вопросов
     */
    @Test
    public void testAddBasicQuestions() {
        assertEquals(10, quesAns.getNumberOfQuestions(), "Должно быть 10 основных вопросов.");
    }
    /**
     *  Проверяет добавление нового вопроса и ответа
     */
    @Test
    public void testAddQuestionAndAnswer() {
        quesAns.addQuestionAndAnswer("Сколько дней в високосном году?", "366");
        assertEquals(11, quesAns.getNumberOfQuestions(), "После добавления одного вопроса должно быть 11.");
        assertEquals("Сколько дней в високосном году?", quesAns.getQuestion(10));
        assertEquals("366", quesAns.getAnswer(10));
    }
    /**
     * Проверяет извлечение вопроса по индексу
     */
    @Test
    public void testGetQuestion() {
        assertEquals("Чему равен угол равностороннего треугольника?", quesAns.getQuestion(0));
        assertEquals("Какая столица Франции?", quesAns.getQuestion(1));
    }
    /**
     * Проверяет получение ответа по индексу
     */
    @Test
    public void testGetAnswer() {
        assertEquals("60", quesAns.getAnswer(0));
        assertEquals("Париж", quesAns.getAnswer(1));
    }
    /**
     * Проверяет запрос вопроса с недействительным индексом
     */
    @Test
    public void testInvalidQuestionIndex() {
        assertNull(quesAns.getQuestion(100), "Запрос вопроса с недействительным индексом должен вернуть null.");
    }
    /**
     * Проверяет запрос ответа с недействительным индексом
     */
    @Test
    public void testInvalidAnswerIndex() {
        assertNull(quesAns.getAnswer(100), "Запрос ответа с недействительным индексом должен вернуть значение null.");
    }
    /**
     * Проверяет получение количества вопросов
     */
    @Test
    public void testGetNumberOfQuestions() {
        assertEquals(10, quesAns.getNumberOfQuestions(), "Первоначально должно быть 10 вопросов.");
        quesAns.addQuestionAndAnswer("Как называется столица России?", "Москва");
        assertEquals(11, quesAns.getNumberOfQuestions(), "После добавления вопроса должно быть 11 вопросов.");
    }


    /**
     * Проверяет удаление вопроса и ответа по индексу
     */
    @Test
    public void testRemoveQues_and_Ans() {
        assertEquals("Чему равен угол равностороннего треугольника?", quesAns.getQuestion(0));
        assertTrue(quesAns.removeQues_and_Ans(0), "Первый вопрос должен быть успешно удален.");
        assertEquals(9, quesAns.getNumberOfQuestions(), "После удаления одного вопроса должно остаться 9..");
        assertEquals("Какая столица Франции?", quesAns.getQuestion(0), " 'Какая столица Франции?'.");
    }

    /**
     * Проверяет удаление недействительного индекса.
     */
    @Test
    public void testRemoveQuestionAndAnswerInvalidIndex() {
        assertFalse(quesAns.removeQues_and_Ans(100), "Удаление вопроса с недействительным индексом должно вернуть false.");
    }

}
