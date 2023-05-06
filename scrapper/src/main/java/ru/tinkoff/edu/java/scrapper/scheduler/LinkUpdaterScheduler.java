package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdaterService;

import java.time.OffsetDateTime;

@Slf4j
@AllArgsConstructor
@Component
public class LinkUpdaterScheduler {

    private final LinkUpdaterService linkUpdaterService;

    @Scheduled(fixedDelayString = "#{${app.scheduler.interval}}")
    public void update() {
        linkUpdaterService.update();
        log.info("I'm updater. I did my work at {}", OffsetDateTime.now());
    }

}