package ru.urfu.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс хранит данные каждого пользователя: номер вопроса, количество правильных ответов, время начала теста
 */
public class UserTestDataTracker {
    private final Map<String, UserData> dataMap = new HashMap<>();

    /**
     * Начинает тест для пользователя
     * @param userId идентификатор пользователя
     * @param subject название предмета
     */
    public void startTest(String userId, String subject) {
        dataMap.put(userId, new UserData(subject, 0L));
    }

    /**
     * Выбирает вариант для пользователя
     * @param userId идентификатор пользователя
     * @param option номер варианта
     */
    public void selectOption(String userId, Long option) {
        UserData data = dataMap.get(userId);
        if (data != null) {
            data.setOption(option);
        }
    }

    /**
     * Возвращает данные пользователя
     * @param userId идентификатор пользователя
     * @return данные пользователя
     */
    public UserData getUserData(String userId) {
        return dataMap.get(userId);
    }

    /**
     * Переводит пользователя на следующий вопрос
     * @param userId идентификатор пользователя
     */
    public void moveToNextQuestion(String userId) {
        UserData data = dataMap.get(userId);
        if (data != null) {
            data.incrementNumberOfQuestion();
        }
    }

    /**
     * Отмечает ответ пользователя
     * @param userId идентификатор пользователя
     * @param correct правильный ли ответ
     */
    public void markAnswer(String userId, boolean correct) {
        UserData data = dataMap.get(userId);
        if (data != null && correct) {
            data.incrementRightNumberOfQuestion();
        }
    }

    /**
     * Удаляет данные пользователя
     * @param userId идентификатор пользователя
     */
    public void removeUser(String userId) {
        dataMap.remove(userId);
    }

    /**
     * Проверяет, проходит ли пользователь тест
     * @param userId идентификатор пользователя
     * @return true если пользователь в тесте
     */
    public boolean isUserInTest(String userId) {
        return dataMap.containsKey(userId);
    }

    /**
     * Возвращает время, затраченное пользователем на тест
     * @param userId идентификатор пользователя
     * @return время в миллисекундах
     */
    public Long getElapsedTime(String userId) {
        return System.currentTimeMillis() - dataMap.get(userId).getStartTime();
    }
}