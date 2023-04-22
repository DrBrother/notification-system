package ru.tinkoff.edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;

@AllArgsConstructor
@Component
public class ListCommand implements Command {

    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "Показать список отслеживаемых ссылок";
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
        ListLinksResponse response;
        try {
            response = client.getLinks(chatId).block();
        } catch (Exception e) {
//          пока что ловлю Exception, потому что в контроллерах заглушки
            return new SendMessage(chatId, e.getMessage());
        }

        if (response != null && response.size() > 0) {
            StringBuilder message = new StringBuilder("Список отслеживаемых ссылок:").append("\n");
            for (LinkResponse link : response.links()) {
                message.append(link.link()).append("\n");
            }
            return new SendMessage(chatId, message.toString());
        } else {
            return new SendMessage(chatId, "Ни одна ссылка не отслеживается");
        }
    }

}