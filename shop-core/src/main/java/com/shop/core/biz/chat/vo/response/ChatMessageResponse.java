/*
package com.shop.core.biz.chat.vo.response;

import com.shop.core.entity.ChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

*/
/**
 * <pre>
 * Description: 채팅 메시지 응답 VO
 * Date: 2024/03/14 3:35 PM
 * Company: binblur
 * Author : Assistant
 * </pre>
 *//*

@Getter
@Setter
public class ChatMessageResponse {
    private Integer id;
    private Integer roomId;
    private Integer senderId;
    private String content;
    private LocalDateTime sentAt;

    public ChatMessageResponse(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.roomId = chatMessage.getRoomId();
        this.senderId = chatMessage.getSenderId();
        this.content = chatMessage.getContent();
        this.sentAt = chatMessage.getSentAt();
    }
}*/
