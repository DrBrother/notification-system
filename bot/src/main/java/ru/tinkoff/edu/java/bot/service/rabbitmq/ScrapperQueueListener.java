package ru.tinkoff.edu.java.bot.service.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.SendNotificationService;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;

@Service
@RequiredArgsConstructor
public class ScrapperQueueListener {

    private final SendNotificationService sendNotificationService;

    @RabbitListener(queues = "#{${app.queueName}}")
    public void listen(LinkUpdate update) {
        sendNotificationService.linkHasUpdates(new LinkUpdate(
                update.id(), update.url(), update.description(), update.tgChatIds()));
    }

}