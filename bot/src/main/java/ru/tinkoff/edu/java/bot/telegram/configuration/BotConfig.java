package ru.tinkoff.edu.java.bot.telegram.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.configuration.webClient.ScrapperClientConfiguration;
import ru.tinkoff.edu.java.bot.telegram.LinkStalkerBot;
import ru.tinkoff.edu.java.bot.telegram.command.*;
import ru.tinkoff.edu.java.bot.telegram.processor.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.telegram.processor.UserMessageProcessorImpl;

import java.util.List;

@Configuration
@AllArgsConstructor
public class BotConfig {

    private ApplicationConfig applicationConfig;
    private ScrapperClientConfiguration configuration;

    @Bean
    LinkStalkerBot getTelegramBot() {
        TelegramBot telegramBot = new TelegramBot(applicationConfig.bot().token());
        BotCommand[] botCommands = messageProcessor().commands().stream().map(Command::toApiCommand).toArray(BotCommand[]::new);
        telegramBot.execute(new SetMyCommands(botCommands));
        return new LinkStalkerBot(telegramBot, messageProcessor());
    }

    @Bean
    UserMessageProcessor messageProcessor() {
        List<? extends Command> commands = List.of(new HelpCommand(), new StartCommand(configuration.scrapperWebClient()),
                new ListCommand(configuration.scrapperWebClient()), new TrackCommand(configuration.scrapperWebClient()),
                new UntrackCommand(configuration.scrapperWebClient()));
        return new UserMessageProcessorImpl(commands);
    }

}