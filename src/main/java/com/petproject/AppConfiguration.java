package com.petproject;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.petproject.logger.C2FLogger;
import com.petproject.logger.ILogHandler;
import com.petproject.logger.RESTHandler;
import com.petproject.logger.StdOutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.springframework.util.StringUtils.hasText;

@Configuration
@EnableCaching
public class AppConfiguration {

    //    настройка для жизни кеша, в данном случае 5 минут
    @Bean
    public CacheManager temporaryCacheManager() {
        final CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES));
        return caffeineCacheManager;
    }

    @Bean
    public C2FLogger logger(
        @Value("${c2f.log.level}") final String levelName,
        @Value("${c2f.log.restUrl}") final String restUrl
    ) {

        C2FLogger.LogLevel level;
        try {
            level = C2FLogger.LogLevel.valueOf(levelName);
        } catch (Exception e) {
            System.out.println("ERROR setting log level to \"" + levelName + "\", defaulting to INFO: " + e.getMessage());
            level = C2FLogger.LogLevel.INFO;
        }
        // Handlers - stdout and REST handler if we have a target IP.
        final ArrayList<ILogHandler> handlers = new ArrayList<>();
        handlers.add(new StdOutHandler());
        if (hasText(restUrl)) {
            handlers.add(new RESTHandler(restUrl));
        }
        C2FLogger.initAppConfig("PricingApi", level, handlers.toArray(new ILogHandler[]{}));
        return new C2FLogger();
    }
}