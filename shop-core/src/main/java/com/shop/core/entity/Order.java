package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description: order Entity
 * Company: home
 * Author : park
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Order", description = "주문 Entity")
public class Order extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3051513244958954521L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "판매처ID(FK)")
    private Integer sellerId;

    @Schema(description = "판매처명")
    private String sellerNm;

    @Schema(description = "영업일자")
    private LocalDate workYmd;

    @Schema(description = "전표번호")
    private Integer chitNo;

    @Schema(description = "입금유형")
    private String payType;

    @Schema(description = "주문분류")
    private String orderCd;

    @Schema(description = "묶음여부")
    private String bundleYn;

    @Schema(description = "보류여부")
    private String holdYn;

    @Schema(description = "매장판매여부")
    private String onSiteYn;

    @Schema(description = "vat 발행여부")
    private String vatYn;

    @Schema(description = "장끼발행건수")
    private Integer jangGgiCnt;

    @Schema(description = "주문상태")
    private String orderStatCd;

    @Schema(description = "전체수량")
    private Integer totSkuCnt;

    @Schema(description = "전체금액(할인된)")
    private BigDecimal totOrderAmt;

    // 추가 2025-01-17
    @Schema(description = "총반납금액")
    private BigDecimal totBackAmt;

    @Schema(description = "총반납건수")
    private Integer totBackCnt;

    @Schema(description = "총할인금액")
    private BigDecimal totDcAmt;
    // 추가 2025-01-17

    @Schema(description = "물류비코드")
    private String logisCd;

    @Schema(description = "물류비")
    private BigDecimal logisAmt;

    @Schema(description = "사용자상태")
    private String custStatCd;

    @Schema(description = "비고")
    private String orderEtc;

    @Schema(description = "비고인쇄여부")
    private String etcPrintYn;

    @Schema(description = "예약일자")
    private LocalDate resvYmd;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}


