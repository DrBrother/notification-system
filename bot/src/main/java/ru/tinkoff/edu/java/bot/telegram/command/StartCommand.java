package ru.tinkoff.edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

@AllArgsConstructor
@NoArgsConstructor
public class StartCommand implements Command {

    private static final String COMMAND = "/start";
    private static final String DESCRIPTION = "Зарегистрировать пользователя";
    private ScrapperClient client;

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

        try {
            client.registerChat(chatId).block();
        } catch (Exception e) {
//          пока что ловлю Exception, потому что в контроллерах заглушки
            return new SendMessage(chatId, e.getMessage());
        }
        return new SendMessage(chatId, "Чат зарегистрирован");
    }

}