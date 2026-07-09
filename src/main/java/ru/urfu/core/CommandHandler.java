package ru.urfu.core;

import ru.urfu.manager.SubjectManager;
import ru.urfu.model.UserData;
import ru.urfu.model.UserTestDataTracker;

import java.util.List;

/**
 * Обработчик команд бота
 */
public class CommandHandler {
    private final SubjectManager subjectManager;
    private final UserTestDataTracker tracker;

    /**
     * Конструктор обработчика команд
     * @param subjectManager менеджер предметов
     * @param tracker трекер данных пользователей
     */
    public CommandHandler(SubjectManager subjectManager, UserTestDataTracker tracker) {
        this.subjectManager = subjectManager;
        this.tracker = tracker;
    }

    /**
     * Обрабатывает команду /start
     * @param userId идентификатор пользователя
     * @return ответное сообщение
     */
    public List<String> handleStart(String userId) {
        if (tracker.isUserInTest(userId)) {
            tracker.removeUser(userId);
        }
        return List.of(Constants.START);
    }

    /**
     * Обрабатывает команду /help
     * @param userId идентификатор пользователя
     * @return ответное сообщение
     */
    public List<String> handleHelp(String userId) {
        if (tracker.isUserInTest(userId)) {
            tracker.removeUser(userId);
        }
        return List.of(Constants.HELP);
    }

    /**
     * Обрабатывает команду /exitTest
     * @param userId идентификатор пользователя
     * @return ответное сообщение
     */
    public List<String> handleExitTest(String userId) {
        if (tracker.isUserInTest(userId)) {
            tracker.removeUser(userId);
            return List.of(Constants.EXIT_TEST);
        }
        return List.of(Constants.EXIT_TEST_FALSE);
    }

    /**
     * Обрабатывает команду /startTest
     * @param userId идентификатор пользователя
     * @return ответное сообщение
     */
    public List<String> handleStartTest(String userId) {
        tracker.startTest(userId, UserData.NO_SUBJECT);
        StringBuilder out = new StringBuilder("Выберите предмет: ");
        for (String subject : subjectManager.allSubjects()) {
            out.append(subject).append("/");
        }
        return List.of(out.toString());
    }
}