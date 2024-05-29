package com.dcat23.freeproxylist.config;

import com.dcat23.freeproxylist.service.ProxyService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@EnableScheduling
@AllArgsConstructor
public class ScheduledTasks {

    private static final long ONE_HOUR = 3_600_000L;

    private final ProxyService service;

    private final Lock lock = new ReentrantLock();

    /**
     * Will be executed daily at 2:26 PM
     */
    @Scheduled(cron = "8 26 14 * * *")
    public void updateDaily() {

    }

    @PostConstruct
    public void initialFetch() {
        CompletableFuture.runAsync(service::init);
    }

    @Scheduled(
            fixedRate = (ONE_HOUR / 6),
            initialDelay = (ONE_HOUR / 6)
    )
    public void updateEveryTenMinutes() {
        CompletableFuture.runAsync(() -> {
            if (lock.tryLock()) {
                try {
                    service.fetch();
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Previous fetch is still running, skipping this execution.");
            }
        });
    }
}
