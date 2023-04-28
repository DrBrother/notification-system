package ru.tinkoff.edu.java.scrapper.service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;

@Service
public class ScrapperQueueProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String queue;

    public ScrapperQueueProducer(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        exchange = properties.exchangeName();
        queue = properties.queueName();
    }

    public void send(LinkUpdate linkUpdate) {
        rabbitTemplate.convertAndSend(exchange, queue, linkUpdate);
    }

}