package ru.tinkoff.edu.java.bot.configuration.webClient;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.client.ScrapperClientImpl;
import ru.tinkoff.edu.java.bot.configuration.ApplicationProperties;


@Configuration
@AllArgsConstructor
public class ScrapperClientConfiguration {

    private WebClient.Builder builder;
    private ApplicationProperties applicationProperties;

    @Bean
    public ScrapperClient scrapperWebClient() {
        return new ScrapperClientImpl(builder.baseUrl(applicationProperties.scrapper().url()).build());
    }

}