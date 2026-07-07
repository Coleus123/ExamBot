package ru.urfu;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;

/**
 * Основной класс для запуска приложения
 */
public class Main {
    public static void main(String[] args) throws TelegramApiException {
        Logic logic = new Logic("src\\main\\resources\\ЕГЭ",
                "src\\main\\resources\\Statistics");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        String BOT_NAME = System.getenv("BOT_NAME");
        String BOT_TOKEN = System.getenv("BOT_TOKEN");
        telegramBotsApi.registerBot(new TelegramBot(BOT_NAME, BOT_TOKEN, logic) {});
    }
}