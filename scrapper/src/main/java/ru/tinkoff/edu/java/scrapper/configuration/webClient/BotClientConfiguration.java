package ru.tinkoff.edu.java.scrapper.configuration.webClient;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;

@Configuration
@AllArgsConstructor
public class BotClientConfiguration {

    private static final String BOT = "bot";
    private WebClient.Builder builder;
    private ApplicationProperties applicationProperties;

    @Bean(BOT)
    public WebClient botWebClient() {
        return builder.baseUrl(applicationProperties.clients().bot()).build();
    }

}