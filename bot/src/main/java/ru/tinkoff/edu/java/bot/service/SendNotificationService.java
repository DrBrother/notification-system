package ru.tinkoff.edu.java.bot.service;

import ru.tinkoff.edu.java.common.dto.LinkUpdate;

public interface SendNotificationService {

    void linkHasUpdates(LinkUpdate update);

}