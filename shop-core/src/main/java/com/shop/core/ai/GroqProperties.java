package com.shop.core.ai;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "groq")
public class GroqProperties {
    private String apiKey;
    private String baseUrl;
    private String model;
}
