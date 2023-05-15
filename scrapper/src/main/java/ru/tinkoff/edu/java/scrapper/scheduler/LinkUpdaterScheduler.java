package ru.tinkoff.edu.java.scrapper.scheduler;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdaterService;

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
