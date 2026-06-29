package com.shop.api.frontWeb.controller;

import com.shop.api.ai.GroqChatService;
import com.shop.api.annotation.GuestUser;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.ai.vo.AiChatRequest;
import com.shop.core.ai.vo.AiChatResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.GuestToken;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontWeb/ai")
@Tag(name = "AiChatController", description = "AI 상품 채팅 API")
@RequiredArgsConstructor
public class AiChatController {

    private final GroqChatService groqChatService;

    @NotAuthRequired
    @PostMapping("/productChat")
    @Operation(summary = "상품 설명 AI 채팅")
    public ApiResponse<AiChatResponse.ChatResult> productChat(
            @Parameter(hidden = true) @GuestUser GuestToken guestUser,
            @RequestBody AiChatRequest.ProductChat request
    ) {
        String content = groqChatService.chat(request, guestUser.getPartnerId());
        AiChatResponse.ChatResult result = new AiChatResponse.ChatResult();
        result.setContent(content);
        return new ApiResponse<>(ApiResultCode.SUCCESS, result);
    }
}
