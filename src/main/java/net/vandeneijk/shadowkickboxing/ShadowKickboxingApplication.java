package net.vandeneijk.shadowkickboxing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class ShadowKickboxingApplication {

    private static final Logger logger = LoggerFactory.getLogger(ShadowKickboxingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShadowKickboxingApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        int cores = 3;
        logger.info("Number of cores available for async processing: " + cores + ".");

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cores);
        executor.setMaxPoolSize(cores);
        executor.setThreadPriority(2);
        executor.setQueueCapacity(1500);
        executor.setThreadNamePrefix("skb-thread-");
        executor.initialize();
        return executor;
    }
}
