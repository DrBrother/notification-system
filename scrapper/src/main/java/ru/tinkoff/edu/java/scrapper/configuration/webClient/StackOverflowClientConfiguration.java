package ru.tinkoff.edu.java.scrapper.configuration.webClient;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;

@Configuration
@AllArgsConstructor
public class StackOverflowClientConfiguration {

    private static final String STACKOVERFLOW = "stackoverflow";
    private WebClient.Builder builder;
    private ApplicationProperties applicationProperties;

    @Bean(STACKOVERFLOW)
    public WebClient stackOverflowWebClient() {
        return builder.baseUrl(applicationProperties.clients().stackoverflow()).build();
    }

}