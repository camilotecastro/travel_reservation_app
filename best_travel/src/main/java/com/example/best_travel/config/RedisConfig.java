package com.example.best_travel.config;

import com.example.best_travel.util.constants.CacheConstants;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig {

  @Value(value = "${cache.redis.address}")
  private String serverAddress;
  @Value(value = "${cache.redis.password}")
  private String serverPassword;

  @Bean
  public RedissonClient getRedissonClient() {
    var config = new Config();
    config.useSingleServer().setAddress(serverAddress).setPassword(serverPassword);
    return Redisson.create(config);
  }

  @Bean
  public CacheManager getCacheManager(RedissonClient redissonClient) {
    var config = new HashMap<String, CacheConfig>();
    config.put("defaultCache", new CacheConfig(60000, 300000)); // TTL: 60s, MaxIdleTime: 300s
    return new RedissonSpringCacheManager(redissonClient, config);
  }

  @Scheduled(cron = CacheConstants.SCHEDULED_RESET_CACHE)
  @Async
  @CacheEvict(cacheNames = {
      CacheConstants.HOTEL_CACHE_NAME,
      CacheConstants.FLY_CACHE_NAME
  }, allEntries = true)
  public void eliminarCache() {
    log.info("eliminando cache");
  }


}
