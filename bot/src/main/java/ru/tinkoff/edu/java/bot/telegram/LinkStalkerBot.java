package ru.tinkoff.edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import java.util.List;
import ru.tinkoff.edu.java.bot.service.MetricService;
import ru.tinkoff.edu.java.bot.telegram.processor.UserMessageProcessor;

public class LinkStalkerBot implements Bot {

    private final TelegramBot telegramBot;
    private final UserMessageProcessor userMessageProcessor;
    private final MetricService metricService;

    public LinkStalkerBot(TelegramBot telegramBot, UserMessageProcessor userMessageProcessor, MetricService metricService) {
        this.telegramBot = telegramBot;
        this.userMessageProcessor = userMessageProcessor;
        this.telegramBot.setUpdatesListener(this);
        this.metricService = metricService;
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
            metricService.incrementHandledMessageCount();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        telegramBot.shutdown();
    }

}
