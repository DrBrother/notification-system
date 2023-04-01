package ru.tinkoff.edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;

import java.net.MalformedURLException;
import java.net.URL;

@AllArgsConstructor
@NoArgsConstructor
public class UntrackCommand implements Command {

    private static final String COMMAND = "/untrack";
    private static final String DESCRIPTION = "Прекратить отслеживание ссылки";
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
        String link = getLink(update);

        if (link != null) {
            try {
                RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(new URL(link));
                LinkResponse response;
                try {
                    response = client.deleteLink(chatId, removeLinkRequest).block();
                } catch (Exception e) {
//                  пока что ловлю Exception, потому что в контроллерах заглушки
                    return new SendMessage(chatId, e.getMessage());
                }
                return new SendMessage(chatId, "Ссылка удалена: " + response.link());
            } catch (MalformedURLException e) {
                return new SendMessage(chatId, "Ссылка не соответствует формату URL");
            }
        } else {
            return new SendMessage(chatId, "Ссылка не указана");
        }
    }

    private String getLink(Update update) {
        String[] text = update.message().text().split(" ");
        if (text.length > 1) {
            return text[1];
        }
        return null;
    }

}