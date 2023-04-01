package ru.tinkoff.edu.java.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;

import java.net.MalformedURLException;
import java.net.URL;

@AllArgsConstructor
@NoArgsConstructor
public class TrackCommand implements Command {

    private static final String COMMAND = "/track";
    private static final String DESCRIPTION = "Начать отслеживание ссылки";
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
                AddLinkRequest addLinkRequest = new AddLinkRequest(new URL(link));
                LinkResponse response;
                try {
                    response = client.addLink(chatId, addLinkRequest).block();
                } catch (Exception e) {
//                  пока что ловлю Exception, потому что в контроллерах заглушки
                    return new SendMessage(chatId, e.getMessage());
                }
                return new SendMessage(chatId, "Ссылка добавлена: " + response.link());
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