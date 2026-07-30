package ru.urfu.core;

import ru.urfu.manager.SubjectManager;
import ru.urfu.model.UserTestDataTracker;
import ru.urfu.UserStatistics;

import java.util.List;

/**
 * Класс, который работает с текстом для бота
 */
public class Logic {
    private final SubjectManager subjectManager;
    private final UserTestDataTracker tracker;
    private final CommandHandler commandHandler;
    private final TestStateHandler testStateHandler;

    public Logic(String filePath, String statFilePath) {
        this.subjectManager = new SubjectManager();
        this.subjectManager.populateData(filePath);
        this.tracker = new UserTestDataTracker();
        UserStatistics userStatistics = new UserStatistics();
        userStatistics.populateStatistics(statFilePath);
        this.commandHandler = new CommandHandler(subjectManager, tracker, userStatistics);
        this.testStateHandler = new TestStateHandler(subjectManager, tracker, userStatistics);
    }

    /**
     * Возвращает текст после обработки
     */
    public List<String> ResponseMessage(String text, String userId) {
        switch (text) {
            case "/start":
                return commandHandler.handleStart(userId);
            case "/help":
                return commandHandler.handleHelp(userId);
            case "/exitTest":
                return commandHandler.handleExitTest(userId);
            case "/startTest":
                return commandHandler.handleStartTest(userId);
            case "/statistic":
                return commandHandler.handleStatistic(userId);
            default:
                if (tracker.isUserInTest(userId)) {
                    return testStateHandler.handleTestInput(userId, text);
                }
                return List.of(Constants.INPUT_PREFIX + text);
        }
    }
}