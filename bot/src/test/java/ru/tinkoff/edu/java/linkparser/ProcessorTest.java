package ru.tinkoff.edu.java.linkparser;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.client.ScrapperClientImpl;
import ru.tinkoff.edu.java.bot.telegram.command.ListCommand;
import ru.tinkoff.edu.java.bot.telegram.command.StartCommand;
import ru.tinkoff.edu.java.bot.telegram.command.TrackCommand;
import ru.tinkoff.edu.java.bot.telegram.command.UntrackCommand;
import ru.tinkoff.edu.java.bot.telegram.processor.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.telegram.processor.UserMessageProcessorImpl;

public class ProcessorTest {

    private static final Long CHAT_ID = 1L;
    public static final String UNKNOWN_COMMAND = "Неизвестная команда";
    public static final String PARAMETERS = "parameters";


    private final ScrapperClient client = Mockito.mock(ScrapperClientImpl.class);

    private final UserMessageProcessor processor = new UserMessageProcessorImpl(List.of(new ListCommand(client),
            new StartCommand(client), new TrackCommand(client), new UntrackCommand(client)));

    @Test
    void processUnknownCommand() {
        SendMessage expected = new SendMessage(CHAT_ID, UNKNOWN_COMMAND);

        Update update = createUpdateWithText("/unknown");

        SendMessage result = processor.process(update);

        Assertions.assertEquals(ReflectionTestUtils.getField(result, PARAMETERS),
                (ReflectionTestUtils.getField(expected, PARAMETERS)));
    }

    @Test
    void processKnownCommand() {
        SendMessage notExpected = new SendMessage(CHAT_ID, UNKNOWN_COMMAND);

        Update update = createUpdateWithText("/start");

        SendMessage result = processor.process(update);

        Assertions.assertNotEquals(ReflectionTestUtils.getField(result, PARAMETERS),
                (ReflectionTestUtils.getField(notExpected, PARAMETERS)));
    }

    private Update createUpdateWithText(String text) {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        ReflectionTestUtils.setField(chat, "id", CHAT_ID);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", text);
        ReflectionTestUtils.setField(update, "message", message);
        return update;
    }

}
