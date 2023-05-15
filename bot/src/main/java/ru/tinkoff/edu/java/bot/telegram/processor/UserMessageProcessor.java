package ru.tinkoff.edu.java.bot.telegram.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import ru.tinkoff.edu.java.bot.telegram.command.Command;

public interface UserMessageProcessor {

    List<? extends Command> commands();

    SendMessage process(Update update);

}
