package com.example.best_travel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Value(value = "${api.base.url}")
  public String baseUrl;
  @Value(value = "${api.api.key}")
  private String apiKey;
  @Value(value = "${api.api.key.header}")
  private String apiKeyHeader;

  @Primary
  @Bean(name = "currency")
  public WebClient currencyWebClient() {
    return WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(apiKeyHeader, apiKey)
        .build();
  }

  //@Primary // Use this bean as default
  @Bean(name = "generic")
  public WebClient genericWebClient() {
    return WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader(apiKeyHeader, apiKey)
        .build();
  }


}
