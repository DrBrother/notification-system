package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.telegram.Bot;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;

@AllArgsConstructor
@Service
public class SendNotificationServiceImpl implements SendNotificationService {

    private final Bot bot;

    @Override
    public void linkHasUpdates(LinkUpdate update) {
        for (Long chatId : update.tgChatIds()) {
            bot.execute(new SendMessage(chatId, "Обновления доступны в : " + update.url()
            + "\n" + update.description()));
        }
    }

}