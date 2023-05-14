package ru.tinkoff.edu.java.bot.telegram.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.configuration.ApplicationProperties;
import ru.tinkoff.edu.java.bot.service.MetricService;
import ru.tinkoff.edu.java.bot.telegram.LinkStalkerBot;
import ru.tinkoff.edu.java.bot.telegram.command.Command;
import ru.tinkoff.edu.java.bot.telegram.processor.UserMessageProcessorImpl;

@Configuration
@AllArgsConstructor
public class BotConfig {

    private ApplicationProperties applicationProperties;
    private List<Command> commands;
    private MetricService metricService;

    @Bean
    LinkStalkerBot getTelegramBot() {
        TelegramBot telegramBot = new TelegramBot(applicationProperties.bot().token());
        BotCommand[] botCommands = commands.stream().map(Command::toApiCommand).toArray(BotCommand[]::new);
        telegramBot.execute(new SetMyCommands(botCommands));
        return new LinkStalkerBot(telegramBot, new UserMessageProcessorImpl(commands), metricService);
    }

}
