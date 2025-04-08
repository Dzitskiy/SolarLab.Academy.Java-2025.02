package com.solarl.education.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(100)                               //максимальное кол-во записей
                .expireAfterWrite(10, TimeUnit.MINUTES) //удаление через 10 минут после записи
                .expireAfterAccess(5, TimeUnit.MINUTES) //удаление через 5 минут неиспользования
                .recordStats());                                //включение сбора статистики
        return cacheManager;
    }
}
