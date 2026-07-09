// TestStateHandler.java - обновляем использование SubjectManager
package ru.urfu.core;

import ru.urfu.model.QuesAns;
import ru.urfu.manager.SubjectManager;
import ru.urfu.model.UserData;
import ru.urfu.model.UserTestDataTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработчик состояния теста
 */
public class TestStateHandler {
    private final SubjectManager subjectManager;
    private final UserTestDataTracker tracker;

    /**
     * Конструктор обработчика состояния теста
     * @param subjectManager менеджер предметов
     * @param tracker трекер данных пользователей
     */
    public TestStateHandler(SubjectManager subjectManager, UserTestDataTracker tracker) {
        this.subjectManager = subjectManager;
        this.tracker = tracker;
    }

    /**
     * Обрабатывает ввод пользователя во время теста
     * @param userId идентификатор пользователя
     * @param text текст ввода
     * @return ответные сообщения
     */
    public List<String> handleTestInput(String userId, String text) {
        UserData data = tracker.getUserData(userId);

        if (!data.isSubjectSelected()) {
            return handleSubjectSelection(userId, text);
        }

        if (data.getOption() == 0L) {
            return handleOptionSelection(userId, text, data);
        }

        return handleAnswer(userId, text, data);
    }

    /**
     * Обрабатывает выбор предмета
     */
    private List<String> handleSubjectSelection(String userId, String text) {
        if (subjectManager.hasSubject(text)) {
            tracker.startTest(userId, text);
            String result = "Введите вариант, всего доступно " +
                    subjectManager.quantityVariants(text) + " вариантов";
            return List.of(result);
        }

        StringBuilder result = new StringBuilder("Нет такого предмета. Доступны следующие предметы: ");
        for (String subject : subjectManager.allSubjects()) {
            result.append(subject).append("/");
        }
        return List.of(result.toString());
    }

    /**
     * Обрабатывает выбор варианта
     */
    private List<String> handleOptionSelection(String userId, String text, UserData data) {
        try {
            int number = Integer.parseInt(text);
            String subject = data.getSubject();
            QuesAns variant = subjectManager.getVariant(subject, number);

            if (variant != null) {
                tracker.selectOption(userId, (long) number);
                String question = variant.getQuestion(data.getNumberOfQuestion().intValue());
                return List.of(question);
            }
            return List.of("Нет такого варианта. Введите снова");
        } catch (NumberFormatException e) {
            return List.of("Ошибка: некорректный формат числа");
        }
    }

    /**
     * Обрабатывает ответ на вопрос
     */
    private List<String> handleAnswer(String userId, String text, UserData data) {
        List<String> output = new ArrayList<>();
        String subject = data.getSubject();
        int option = data.getOption().intValue();
        int questionIndex = data.getNumberOfQuestion().intValue();

        QuesAns variant = subjectManager.getVariant(subject, option);
        if (variant == null) {
            return List.of("Ошибка: вариант не найден");
        }

        String rightAnswer = variant.getAnswer(questionIndex);
        boolean isCorrect = text.equalsIgnoreCase(rightAnswer);

        tracker.markAnswer(userId, isCorrect);
        tracker.moveToNextQuestion(userId);

        output.add(isCorrect ? Constants.RIGHT_ANSWER : Constants.WRONG_ANSWER);

        if (data.getNumberOfQuestion() == variant.getNumberOfQuestions()) {
            String result = "Тест завершен за " + tracker.getElapsedTime(userId) / 1_000 +
                    " секунд. Правильное количество ответов - " +
                    data.getRightNumberOfQuestion() + "/" + variant.getNumberOfQuestions() +
                    ". Пройти тест заново или выйти?";
            output.add(result);
        } else if (data.getNumberOfQuestion() < variant.getNumberOfQuestions()) {
            String question = variant.getQuestion(data.getNumberOfQuestion().intValue());
            output.add(question);
        } else {
            output.clear();
            output.add("Пройти тест заново или выйти?");
        }

        return output;
    }
}