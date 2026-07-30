package ru.urfu.core;

import ru.urfu.manager.SubjectManager;
import ru.urfu.model.UserData;
import ru.urfu.model.UserTestDataTracker;
import ru.urfu.UserStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработчик команд бота
 */
public class CommandHandler {
    private final SubjectManager subjectManager;
    private final UserTestDataTracker tracker;
    private final UserStatistics userStatistics;

    /**
     * Конструктор обработчика команд
     * @param subjectManager менеджер предметов
     * @param tracker трекер данных пользователей
     * @param userStatistics статистика пользователей
     */
    public CommandHandler(SubjectManager subjectManager, UserTestDataTracker tracker, UserStatistics userStatistics) {
        this.subjectManager = subjectManager;
        this.tracker = tracker;
        this.userStatistics = userStatistics;
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

    /**
     * Обрабатывает команду /statistic
     * @param userId идентификатор пользователя
     * @return ответное сообщение со статистикой
     */
    public List<String> handleStatistic(String userId) {
        List<String> output = new ArrayList<>();
        List<String> subjects = subjectManager.allSubjects();
        output.add("Статистика за последние 5 вариантов по каждому предмету:");
        for (String sub : subjects) {
            if (userStatistics.check(sub, userId)) {
                String result = sub + ": средний балл " +
                        userStatistics.getAverageScore(sub, userId) + "/" +
                        subjectManager.getVariant(sub, 1).getNumberOfQuestions()
                        + ", среднее время выполнения задач -";
                Long time = userStatistics.getAverageTime(sub, userId);
                Long hours = time / 3600000;
                time %= 3600000;
                Long minutes = time / 60000;
                time %= 60000;
                Long seconds = time / 1000;
                if (hours > 0) {
                    result += " " + hours + " час";
                }
                if (minutes > 0) {
                    result += " " + minutes + " минут";
                }
                if (seconds > 0) {
                    result += " " + seconds + " секунд";
                }
                output.add(result + ".");
            } else {
                output.add(sub + ": не решено ни одного варианта.");
            }
        }
        return output;
    }
}