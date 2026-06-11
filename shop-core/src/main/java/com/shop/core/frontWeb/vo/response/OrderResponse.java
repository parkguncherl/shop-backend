package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    @Getter
    @Setter
    @Schema(name = "OrderResponseInfo", description = "Order response", type = "object")
    public static class Info {
        private Long orderId;
        private String orderNo;
        private Long socialAccountId;
        private String orderStatus;
        private Long productAmount;
        private Long discountAmount;
        private Long usedPoint;
        private Long paymentAmount;
        private Long earnedPoint;
        private LocalDateTime creTm;
        private List<Item> items;
    }

    @Getter
    @Setter
    @Schema(name = "OrderResponseItem", description = "Order item response", type = "object")
    public static class Item {
        private Long orderItemId;
        private Long productId;
        private Long productDetId;
        private String productName;
        private String productImage;
        private String optionName;
        private Integer quantity;
        private Long unitPrice;
        private Long discountAmount;
        private Long paymentAmount;
    }
}
