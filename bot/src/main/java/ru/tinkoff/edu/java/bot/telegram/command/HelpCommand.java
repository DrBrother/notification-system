package ru.tinkoff.edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public class HelpCommand implements Command {

    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "Вывести окно с командами";

    private final List<Command> commands = List.of(this, new StartCommand(), new ListCommand(),
            new TrackCommand(), new UntrackCommand());

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