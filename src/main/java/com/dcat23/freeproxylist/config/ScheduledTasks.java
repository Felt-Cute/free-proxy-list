package com.dcat23.freeproxylist.config;

import com.dcat23.freeproxylist.service.ProxyService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@AllArgsConstructor
public class ScheduledTasks {


    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = 60_000L;
    private static final long ONE_HOUR = 3_600_000L;

    private final ProxyService service;

    @PostConstruct
    public void updateAtStart() {
        service.fetch();
    }

    /**
     * Will be executed daily at 2:26 PM
     */
    @Scheduled(cron = "8 26 14 * * *")
    public void updateDaily() {

    }

    /**
     * 3600000 milliseconds = 1 hour
     */
//    @Scheduled(fixedRate = (ONE_HOUR))
//    public void updateHourly() {
//        service.fetch();
//    }

    @Scheduled(fixedRate = (ONE_HOUR / 6))
    public void updateEveryTenMinutes() {
        service.fetch();
    }
}
