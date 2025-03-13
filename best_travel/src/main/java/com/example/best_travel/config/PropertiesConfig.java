package com.example.best_travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource(value = "classpath:configs/api_currency.properties"),
    @PropertySource(value = "classpath:configs/redis.properties")
})
public class PropertiesConfig {

}
