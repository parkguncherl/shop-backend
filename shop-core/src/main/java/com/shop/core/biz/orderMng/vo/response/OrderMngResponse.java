package com.shop.core.biz.orderMng.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class OrderMngResponse {

    @Getter
    @Setter
    @Schema(name = "OrderMngResponseBoListItem", description = "BO 주문 목록 항목", type = "object")
    public static class BoListItem {
        private Long orderId;
        private String orderNo;
        private String orderStatus;
        private String orderStatusNm;
        private Long productAmount;
        private Long usedPoint;
        private Long paymentAmount;
        private LocalDateTime creTm;
        private Long paymentSeq;
        private String paymentStatus;
        private String paymentStatusNm;
        private Integer itemCount;
        private String topProductName;
    }
}
