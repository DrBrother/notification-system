package ru.tinkoff.edu.java.scrapper.configuration.webClient;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;

@Configuration
@AllArgsConstructor
public class StackOverflowClientConfiguration {

    private static final String STACKOVERFLOW = "stackoverflow";
    private WebClient.Builder builder;
    private ApplicationConfig applicationConfig;

    @Bean(STACKOVERFLOW)
    public WebClient stackOverflowWebClient() {
        return builder.baseUrl(applicationConfig.stackoverflow()).build();
    }

}