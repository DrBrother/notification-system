package ru.tinkoff.edu.java.scrapper.configuration.webClient;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;

@Configuration
@AllArgsConstructor
public class GitHubClientConfiguration {

    private static final String GITHUB = "github";
    private WebClient.Builder builder;
    private ApplicationProperties applicationProperties;

    @Bean(GITHUB)
    public WebClient gitHubWebClient() {
        return builder.baseUrl(applicationProperties.clients().github()).build();
    }

}