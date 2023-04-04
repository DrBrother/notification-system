package ru.tinkoff.edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class HelpCommand implements Command {

    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "Вывести окно с командами";

    private List<Command> commands;

    @PostConstruct
    private void postConstruct(){
        commands.add(this);
    }

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        StringBuilder response = new StringBuilder("Список команд:").append("\n");
        for (Command command : commands) {
            response.append(command.commandDescriptionString()).append("\n");
        }

        return new SendMessage(chatId, response.toString());
    }

}