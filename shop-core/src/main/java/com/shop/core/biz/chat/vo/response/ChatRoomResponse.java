/*
package com.shop.core.biz.chat.vo.response;

import com.shop.core.entity.ChatRoom;
import com.shop.core.enums.BooleanValueCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

*/
/**
 * <pre>
 * Description: 채팅방 응답 VO
 * Date: 2024/03/14 4:00 PM
 * Company: binblur
 * Author : Assistant
 * </pre>
 *//*

@Getter
@Setter
public class ChatRoomResponse {
    private Integer id;
    private String roomName;
    private Integer partnerId;
    private Integer adminId;
    private BooleanValueCode activeYn;
    private LocalDateTime createdAt;
    private LocalDateTime lastMessageAt;

    public ChatRoomResponse(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
        this.partnerId = chatRoom.getPartnerId();
        this.adminId = chatRoom.getAdminId();
        this.activeYn = chatRoom.getActiveYn();
        this.createdAt = chatRoom.getCreatedAt();
        this.lastMessageAt = chatRoom.getLastMessageAt();
    }
}*/
