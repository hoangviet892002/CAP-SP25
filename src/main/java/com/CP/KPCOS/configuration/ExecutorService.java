package com.CP.KPCOS.configuration;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync(proxyTargetClass = true)
@ConfigurationProperties(prefix = "executor")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecutorService {
    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private String threadNamePrefix;

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }

    @Bean(name = "syncExecutor")
    public Executor syncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("SyncExecutor");
        executor.initialize();
        return executor;
    }

    @Bean(name = "notificationExecutor")
    public Executor notificationExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("NotificationExecutor");
        executor.initialize();
        return executor;
    }
}
