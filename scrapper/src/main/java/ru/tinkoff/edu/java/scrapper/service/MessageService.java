package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;
import ru.tinkoff.edu.java.scrapper.service.rabbitmq.ScrapperQueueProducer;

@Service
public class MessageService {

    private final boolean useQueue;
    private final BotClient botClient;
    private final ScrapperQueueProducer producer;

    public MessageService(ApplicationProperties properties, BotClient botClient, ScrapperQueueProducer producer) {
        this.useQueue = properties.useQueue();
        this.botClient = botClient;
        this.producer = producer;
    }

    public void sendMessage(LinkUpdate linkUpdate) {
        if (useQueue) {
            producer.send(linkUpdate);
        } else {
            botClient.pullLinks(linkUpdate).block();
        }
    }

}