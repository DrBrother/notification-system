package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.linkparser.AbstractParser;
import ru.tinkoff.edu.java.linkparser.Parser;
import ru.tinkoff.edu.java.linkparser.impl.GitHubParser;
import ru.tinkoff.edu.java.linkparser.impl.StackOverflowParser;

@Configuration
public class ApplicationConfig {

    @Bean
    public Parser linkParser() {
        return AbstractParser.of(new GitHubParser(), new StackOverflowParser());
    }

}