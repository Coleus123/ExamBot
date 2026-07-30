package ru.urfu.model;

/**
 * Хранит данные пользователя
 */
public class UserData {
    private String subject;
    private Long option;
    private Long numberOfQuestion;
    private Long rightNumberOfQuestion;
    private Long startTime;

    public static final String NO_SUBJECT = "None";

    /**
     * Конструктор для создания данных пользователя
     * @param subject название предмета
     * @param option номер варианта
     */
    public UserData(String subject, Long option) {
        this.subject = subject;
        this.option = option;
        this.numberOfQuestion = 0L;
        this.rightNumberOfQuestion = 0L;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Возвращает предмет
     */
    public String getSubject() { return subject; }

    /**
     * Возвращает номер варианта
     */
    public Long getOption() { return option; }

    /**
     * Возвращает номер вопроса, на котором сейчас пользователь
     */
    public Long getNumberOfQuestion() { return numberOfQuestion; }

    /**
     * Возвращает количество правильно отвеченных вопросов
     */
    public Long getRightNumberOfQuestion() { return rightNumberOfQuestion; }

    /**
     * Возвращает время начала теста
     */
    public Long getStartTime() { return startTime; }

    /**
     * Меняет номер варианта
     */
    public void setOption(Long option) { this.option = option; }

    /**
     * Переводит пользователя на следующий вопрос
     */
    public void incrementNumberOfQuestion() { this.numberOfQuestion++; }

    /**
     * Увеличивает количество правильных ответов
     */
    public void incrementRightNumberOfQuestion() { this.rightNumberOfQuestion++; }

    /**
     * Проверяет, выбран ли предмет
     * @return true если предмет выбран
     */
    public boolean isSubjectSelected() {
        return !NO_SUBJECT.equals(subject);
    }
}