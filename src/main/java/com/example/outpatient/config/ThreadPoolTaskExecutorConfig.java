package com.example.outpatient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


@Configuration
public class ThreadPoolTaskExecutorConfig {

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int coreSize = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(coreSize * 2 + 1);
        executor.setQueueCapacity(100); // Size of the queue
        executor.setKeepAliveSeconds(3); // Maximum lifetime of idle excess threads
        executor.setThreadNamePrefix("thread-execute-"); // Prefix for thread names
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}