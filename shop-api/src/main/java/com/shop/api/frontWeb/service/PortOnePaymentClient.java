package com.shop.api.frontWeb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PortOnePaymentClient {

    @Value("${portone.api.base-url:https://api.portone.io}")
    private String baseUrl;

    @Value("${portone.api.secret:}")
    private String apiSecret;

    @Value("${portone.store-id:}")
    private String storeId;

    public String cancelPayment(String paymentId, String reason) {
        if (!StringUtils.hasText(apiSecret)) {
            throw new IllegalStateException("portone.api.secret is not configured.");
        }

        Map<String, Object> body = new HashMap<>();
        if (StringUtils.hasText(storeId)) {
            body.put("storeId", storeId);
        }
        if (StringUtils.hasText(reason)) {
            body.put("reason", reason);
        }
        body.put("requester", "CUSTOMER");

        try {
            String idempotencyKey = "\"cancel-" + paymentId + "\"";
            return RestClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeader("Authorization", "PortOne " + apiSecret)
                    .build()
                    .post()
                    .uri("/payments/{paymentId}/cancel", paymentId)
                    .header("Idempotency-Key", idempotencyKey)
                    .body(body)
                    .retrieve()
                    .body(String.class);
        } catch (RestClientResponseException e) {
            String responseBody = e.getResponseBodyAsString();
            throw new IllegalStateException(StringUtils.hasText(responseBody) ? responseBody : e.getMessage(), e);
        }
    }
}
