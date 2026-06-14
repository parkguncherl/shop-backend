package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "OrderItem", description = "FO order item entity")
public class OrderItem {

    private Long id;
    private Long orderId;
    private Long cartId;        // TB_CART.id 참조 (주문 당시 장바구니 아이템)
    private Long productId;
    private Long productDetId;
    private String productName;
    private String productImage;
    private String optionName;
    private Integer quantity;
    private Long unitPrice;
    private Long discountAmount;
    private Long paymentAmount;
    private LocalDateTime creTm;
    private LocalDateTime uptTm;
}
