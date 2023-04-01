package ru.tinkoff.edu.java.linkparser;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.telegram.command.ListCommand;

import java.net.MalformedURLException;
import java.net.URL;

@ExtendWith(MockitoExtension.class)
public class CommandTest {

    private static final Long CHAT_ID = 1L;

    @InjectMocks
    private ListCommand command;
    @Mock
    private ScrapperClient client;

    @Test
    public void testLinksListMessage() throws MalformedURLException {
        LinkResponse[] links = {
                new LinkResponse(1L, new URL("https://github.com/Tinkoff/kora")),
                new LinkResponse(2L, new URL("https://github.com/Tinkoff/zeebe"))
        };
        ListLinksResponse expectedLinksResponse = new ListLinksResponse(links, 2);
        SendMessage expected = expectedMessage(expectedLinksResponse);

        Update update = createUpdate();

        Mockito.when(client.getLinks(Mockito.anyLong())).thenReturn(Mono.just(expectedLinksResponse));

        SendMessage sendMessage = command.handle(update);

        Assertions.assertEquals(ReflectionTestUtils.getField(sendMessage, "parameters"),
                (ReflectionTestUtils.getField(expected, "parameters")));
    }

    @Test
    public void testEmptyLinksListMessage() {
        ListLinksResponse expectedLinksResponse = new ListLinksResponse(new LinkResponse[0], 0);
        SendMessage expected = expectedMessage(expectedLinksResponse);

        Update update = createUpdate();

        Mockito.when(client.getLinks(Mockito.anyLong())).thenReturn(Mono.just(expectedLinksResponse));

        SendMessage sendMessage = command.handle(update);

        Assertions.assertEquals(ReflectionTestUtils.getField(sendMessage, "parameters"),
                (ReflectionTestUtils.getField(expected, "parameters")));
    }

    private SendMessage expectedMessage(ListLinksResponse response) {
        if (response != null && response.size() > 0) {
            StringBuilder message = new StringBuilder("Список отслеживаемых ссылок:").append("\n");
            for (LinkResponse link : response.links()) {
                message.append(link.link()).append("\n");
            }
            return new SendMessage(CHAT_ID, message.toString());
        } else {
            return new SendMessage(CHAT_ID, "Ни одна ссылка не отслеживается");
        }
    }

    private Update createUpdate() {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        ReflectionTestUtils.setField(chat, "id", CHAT_ID);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(update, "message", message);
        return update;
    }

}