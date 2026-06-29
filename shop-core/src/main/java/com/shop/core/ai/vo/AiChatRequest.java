package com.shop.core.ai.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AiChatRequest {

    @Getter
    @Setter
    public static class ProductChat {
        private Integer productId;
        private List<Message> messages;
    }

    @Getter
    @Setter
    public static class Message {
        private String role;    // "user" | "assistant"
        private String content;
    }
}
