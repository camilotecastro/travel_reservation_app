package com.example.best_travel.domain.infrastucture.helpers;


import com.example.best_travel.domain.infrastucture.dtos.CurreyncyDTO;
import java.util.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiCurrencyConnectorHelper {

  private final WebClient webClient;

  @Value(value = "${api.base-currency}")
  private String baseCurrency;

  private static final String BASE_CURREYNCY_QUERY_PARAM = "?base={base}";
  private static final String SYMBOL_CURREYNCY_QUERY_PARAM = "&symbols={symbol}";
  private static final String CURREYNCY_PATH = "/fixer/latest";

  public ApiCurrencyConnectorHelper(WebClient webClient) {
    this.webClient = webClient;
  }

  public CurreyncyDTO getCurrency(Currency currency) {
    return this.webClient.get().uri(uriBuilder -> uriBuilder
            .path(CURREYNCY_PATH)
            .query(BASE_CURREYNCY_QUERY_PARAM)
            .query(SYMBOL_CURREYNCY_QUERY_PARAM)
            .build(baseCurrency, currency.getCurrencyCode()))
        .retrieve()
        .bodyToMono(CurreyncyDTO.class)
        .block();
  }
}
