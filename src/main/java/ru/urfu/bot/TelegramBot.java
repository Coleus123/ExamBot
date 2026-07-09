
package ru.urfu.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.core.Logic;

import java.util.List;


/**
 * Класс телеграмм-бот
 */

public class TelegramBot extends TelegramLongPollingBot{
    private String botToken;
    private String botName;
    private Logic logic;

/**
     *Конструктор телеграмм-бот
     */

    public TelegramBot(String botName, String botToken, Logic logic) {
        this.botName = botName;
        this.botToken = botToken;
        this.logic = logic;
    }


/**
     * Отправляет ответ на сообщение пользователя
     */

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        List<String> output;
        output = logic.ResponseMessage(text, chatId);
        try {
            for (String s : output) {
                sendMessage.setText(s);
                this.execute(sendMessage);
            }

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


/**
     * возвращает бот-токен
     */

    @Override
    public String getBotToken() {
        return botToken;
    }


/**
     * Возвращает имя бота
     */

    @Override
    public String getBotUsername() {
        return botName;
    }
}
