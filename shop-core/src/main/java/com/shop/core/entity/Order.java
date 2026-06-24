package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Order", description = "FO 주문 엔티티")
public class Order {

    @Schema(description = "주문 PK")
    private Long id;

    @Schema(description = "주문 번호 (고객 노출용 식별번호)")
    private String orderNo;

    @Schema(description = "파트너(셀러) ID — GuestToken 에서 유입")
    private Integer partnerId;

    @Schema(description = "소셜 계정 ID — 회원 키 (비회원이면 null)")
    private Long socialAccountId;

    @Schema(description = "주문 상태 (O:주문접수 P:결제완료 R:배송준비 S:배송중 D:배송완료 C:취소)")
    private String orderStatus;

    @Schema(description = "상품 금액 합계 (할인 전)")
    private Long productAmount;

    @Schema(description = "할인 금액 합계")
    private Long discountAmount;

    @Schema(description = "사용 포인트")
    private Long usedPoint;

    @Schema(description = "실결제 금액 (productAmount - discountAmount - usedPoint)")
    private Long paymentAmount;

    @Schema(description = "적립 포인트")
    private Long earnedPoint;

    @Schema(description = "수령인 이름")
    private String receiverName;

    @Schema(description = "수령인 연락처")
    private String receiverPhone;

    @Schema(description = "우편번호")
    private String zipCode;

    @Schema(description = "기본 주소")
    private String address;

    @Schema(description = "상세 주소")
    private String addressDetail;

    @Schema(description = "배송 메모")
    private String memo;

    @Schema(description = "생성 일시")
    private LocalDateTime creTm;

    @Schema(description = "수정 일시")
    private LocalDateTime uptTm;
}
