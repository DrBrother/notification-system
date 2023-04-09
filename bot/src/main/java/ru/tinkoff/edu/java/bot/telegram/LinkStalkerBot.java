package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import ru.tinkoff.edu.java.bot.telegram.processor.UserMessageProcessor;

import java.util.List;

public class LinkStalkerBot implements Bot {

    private final TelegramBot telegramBot;
    private final UserMessageProcessor userMessageProcessor;

    public LinkStalkerBot(TelegramBot telegramBot, UserMessageProcessor userMessageProcessor) {
        this.telegramBot = telegramBot;
        this.userMessageProcessor = userMessageProcessor;
        this.telegramBot.setUpdatesListener(this);
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            SendMessage message = userMessageProcessor.process(update);
            execute(message);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        telegramBot.shutdown();
    }

}