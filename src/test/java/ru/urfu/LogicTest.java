
package ru.urfu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Тест текстовых методов бота
 */

public class LogicTest {
    Logic logic;

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

        File directory1 = new File(tempDir, "Stat");
        directory1.mkdirs();

        File subject1 = new File(directory1, "Математика");
        subject1.mkdirs();

        File user1 = new File(subject1, "user123");
        try (FileWriter writer1 = new FileWriter(user1)) {
            writer1.write("1 5 20000 10000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        logic = new Logic(tempDir.getPath() + "\\Test",
                tempDir.getPath() + "\\Stat");
    }


/**
     * Проверяет, правильно ли выводит введенный пользователем текст с префиксом
     */

    @Test
    public void testResponseMessageInputPrefix() {

        String InputMessage = "Hello World!";
        String ExpectedMessage = "Вы ввели: " + "Hello World!";
        List<String> OutputMessage = logic.ResponseMessage(InputMessage, "user1");
        assertEquals(ExpectedMessage, OutputMessage.get(0));
    }


/**
     * Проверяет, правильно ли выводит текст при начале разговора с пользователем
     */

    @Test
    public void TestResponseMessageStart() {

        String InputMessage = "/start";
        String ExpectedMessage = "Привет! Я Текстовый бот. Напиши /help, чтобы узнать больше";
        List<String> OutputMessage = logic.ResponseMessage(InputMessage, "user2");
        assertEquals(ExpectedMessage, OutputMessage.get(0));
    }

    /**
     * Проверяет, правильно ли выдается статистика
     */
    @Test
    public void testStatistics(){
        String input = "/statistic";
        List<String> output = logic.ResponseMessage(input, "user123");
        assertEquals("Статистика за последние 5 вариантов по каждому предмету:",
                output.get(0));
        assertEquals("Математика: средний балл 3/1, " +
                "среднее время выполнения задач - 15 секунд.", output.get(1));
    }
    /**
     * Проверяет, правильно ли выводит справку, если попросит пользователь
     */

    @Test
    public void testResponseMessageHelp() {
        String InputMessage = "/help";
        String ExpectedMessage = "Я Текстовый бот, у меня есть несколько функций:\n" +
                " 1) Я могу отправлять Вам, что Вы ввели в чат \n " +
                "2) Если напишите /help, то я Вам расскажу о себе" +
                "3) Если напишите /startTest, то запустится режим тестирования, чтобы выйти из режима тестирования напишите /exitTest";
        List<String> OutputMessage = logic.ResponseMessage(InputMessage, "user3");
        assertEquals(ExpectedMessage, OutputMessage.get(0));
    }


/**
     * Проверяет, правильно ли выводится текст, если тест еще не начат
     */

    @Test
    public void testResponseMessageExit1() {
        List<String> outputMessage = logic.ResponseMessage("/exitTest", "user4");
        String expectedMessage = "Тест ещё не начат";
        assertEquals(expectedMessage, outputMessage.get(0));
    }


/**
     * Проверяет, правильно ли выводится текст, если идет тест
     */

    @Test
    public void testResponseMessageExit2() {
        logic.ResponseMessage("/startTest", "user5");
        List<String> outputMessage = logic.ResponseMessage("/exitTest", "user5");
        assertEquals("Вы вышли из теста", outputMessage.get(0));
    }


/**
     * Проверяет, правильно ли выводится вопрос при начале теста
     */

    @Test
    public void testResponseMessageStart() {
        assertEquals("Выберите предмет: Математика/",
                logic.ResponseMessage("/startTest", "user6").get(0));
    }


/**
     * Проверяет, правильно ли выдаются результаты теста
     */

    @Test
    public void testResponseMessageRightTest() throws InterruptedException {
        assertEquals("Выберите предмет: Математика/",
                logic.ResponseMessage("/startTest", "user6").get(0));
        assertEquals("Введите вариант, всего доступно 1 вариантов",
                logic.ResponseMessage("Математика", "user6").get(0));
        assertEquals("Вопрос", logic.ResponseMessage("1", "user6").get(0));
        List<String> outputMessage = logic.ResponseMessage("Ответ", "user6");
        assertEquals("Вы ответили правильно", outputMessage.get(0));
        assertEquals("Тест завершен за 0 секунд." +
                " Правильное количество ответов - 1/1. Пройти тест заново или выйти?", outputMessage.get(1));
    }


/**
     * Проверяет, правильно ли подсчитываются результаты
     */

    @Test
    public void testResponseMessageWrongTest() throws InterruptedException {
        assertEquals("Выберите предмет: Математика/",
                logic.ResponseMessage("/startTest", "user6").get(0));
        assertEquals("Введите вариант, всего доступно 1 вариантов",
                logic.ResponseMessage("Математика", "user6").get(0));
        assertEquals("Нет такого варианта. Введите снова",
                logic.ResponseMessage("2", "user6").get(0));
        assertEquals("Вопрос", logic.ResponseMessage("1", "user6").get(0));
        Thread.sleep(1010);
        List<String> outputMessage = logic.ResponseMessage("Не ответ", "user6");
        assertEquals("Вы ответили неправильно", outputMessage.get(0));
        assertEquals("Тест завершен за 1 секунд." +
                " Правильное количество ответов - 0/1. Пройти тест заново или выйти?", outputMessage.get(1));
    }
}
