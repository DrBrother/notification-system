package ru.tinkoff.edu.java.scrapper.service.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;

@Service
@Slf4j
public class ScrapperQueueListener {

    @RabbitListener(queues = "#{'${app.queueName}' + '.dlq'}")
    public void listen(LinkUpdate update) {
        log.error("Bot can't listen message with id {} and link {}", update.id(), update.url());
    }

}