package ru.tinkoff.edu.java.bot.telegram.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.bot.telegram.command.Command;

@AllArgsConstructor
public class UserMessageProcessorImpl implements UserMessageProcessor {

    private List<? extends Command> commands;

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (Command command : commands()) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        return new SendMessage(update.message().chat().id(), "Неизвестная команда");
    }

}
