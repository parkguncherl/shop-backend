package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class ComuResponse {

    @Getter
    @Setter
    @Schema(name = "ComuResponseThread", description = "상담 스레드 (메시지 목록 포함)", type = "object")
    public static class Thread {
        private Long id;
        private String comuType;
        private String comuTypeName;
        private Long orderId;
        private LocalDateTime creTm;
        private List<Message> messages;
    }

    @Getter
    @Setter
    @Schema(name = "ComuResponseMessage", description = "상담 메시지 단건", type = "object")
    public static class Message {
        private Long id;
        private Long comuId;
        private String reqYn;
        private String comuCntn;
        private Integer fileId;
        private String creUser;
        private LocalDateTime creTm;
        private String readYn;
    }

    @Getter
    @Setter
    @Schema(name = "ComuResponseSummary", description = "주문별 상담 목록 요약", type = "object")
    public static class Summary {
        private Long id;
        private String comuType;
        private String comuTypeName;
        private Long orderId;
        private String lastMessage;
        private LocalDateTime lastMessageTm;
        private LocalDateTime creTm;
        private Integer unreadCount;
    }

    @Getter
    @Setter
    @Schema(name = "ComuResponseProductQna", description = "상품 Q&A 항목", type = "object")
    public static class ProductQna {
        private Long comuId;
        private String question;
        private String creUser;
        private LocalDateTime creTm;
        private String answer;
        private LocalDateTime answerTm;
    }

    @Getter
    @Setter
    @Schema(name = "ComuResponseBoListItem", description = "BO 고객문의 목록 항목", type = "object")
    public static class BoListItem {
        private Long comuId;
        private String comuType;
        private String comuTypeName;
        private Long orderId;
        private String orderNo;
        private LocalDateTime orderDate;
        private String paymentStatus;
        private String paymentStatusName;
        private String topProductName;
        private String lastMessage;
        private LocalDateTime lastMessageTm;
        private String cntCntn;
        private LocalDateTime creTm;
        private String remarkYn;
        private String comment;
    }
}
