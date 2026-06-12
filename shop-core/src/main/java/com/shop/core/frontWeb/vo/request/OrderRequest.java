package com.shop.core.frontWeb.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OrderRequest {

    @Getter
    @Setter
    @Schema(name = "OrderRequestCreate", description = "Order create request", type = "object")
    public static class Create {
        private String orderNo;
        private Long socialAccountId;
        private Long productAmount;
        private Long discountAmount;
        private Long usedPoint;
        private Long paymentAmount;
        private Long earnedPoint;
        private String receiverName;
        private String receiverPhone;
        private String zipCode;
        private String address;
        private String addressDetail;
        private String memo;
        private Delivery delivery;
        private List<Item> items;
    }

    @Getter
    @Setter
    @Schema(name = "OrderRequestDelivery", description = "Order delivery request", type = "object")
    public static class Delivery {
        private String receiverName;
        private String receiverPhone;
        private String zipCode;
        private String address;
        private String addressDetail;
        private String memo;
    }

    @Getter
    @Setter
    @Schema(name = "OrderRequestItem", description = "Order item request", type = "object")
    public static class Item {
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

    @Getter
    @Setter
    @Schema(name = "OrderRequestUpdateStatus", description = "Order status update request", type = "object")
    public static class UpdateStatus {
        private Long orderId;
        private String orderStatus;
    }
}
