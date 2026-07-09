package ru.urfu.core;

/**
 * Содержит константы, используемые в текстовом боте.
 */
public class Constants {

    /**
     * Пользователь неправильно ответил
     */
    public static final String WRONG_ANSWER = "Вы ответили неправильно";
    /**
     * Пользователь правильно ответил
     */
    public static final String RIGHT_ANSWER = "Вы ответили правильно";
    /**
     * Поясняет, что делает бот
     */
    public static final String HELP = """
            Я Текстовый бот, у меня есть несколько функций:
            1) Я могу отправлять Вам, что Вы ввели в чат
            2) Если напишите /help, то я Вам расскажу о себе
            3) Если напишите /startTest, то запустится режим тестирования, чтобы выйти из режима тестирования напишите /exitTest""";
    /**
     * Приветствие при начале работы с ботом
     */
    public static final String START = "Привет! Я Текстовый бот. Напиши /help, чтобы узнать больше";
    /**
     * Дополнение к тексту, который введ пользователь
     */
    public static final String INPUT_PREFIX = "Вы ввели: ";
    /**
     * Тест еще не начат
     */
    public static final String EXIT_TEST_FALSE = "Тест ещё не начат";
    /**
     * Выход из теста
     */
    public static final String EXIT_TEST = "Вы вышли из теста";

    /**
     * Приватный конструктор, чтобы не было возможности добавить константы
     */
    private Constants() {}
}
