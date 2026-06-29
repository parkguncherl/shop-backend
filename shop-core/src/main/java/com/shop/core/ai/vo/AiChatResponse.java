package com.shop.core.ai.vo;

import lombok.Getter;
import lombok.Setter;

public class AiChatResponse {

    @Getter
    @Setter
    public static class ChatResult {
        private String content;
    }
}
