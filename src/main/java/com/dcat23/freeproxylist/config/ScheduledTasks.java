package com.dcat23.freeproxylist.config;

import com.dcat23.freeproxylist.service.ProxyService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@EnableScheduling
@AllArgsConstructor
public class ScheduledTasks {

    private static final long ONE_HOUR = 3_600_000L;

    private final ProxyService service;

    /**
     * Will be executed daily at 2:26 PM
     */
    @Scheduled(cron = "8 26 14 * * *")
    public void updateDaily() {

    }

    @Scheduled(fixedRate = (ONE_HOUR / 6))
    public void updateEveryTenMinutes() {
        CompletableFuture.runAsync(service::fetch);
    }
}
