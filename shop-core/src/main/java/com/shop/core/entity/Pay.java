package com.shop.core.entity;

import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import com.google.gson.GsonBuilder;
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
 * Description: pay Entity
 * Company: home
 * </pre>
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Pay", description = "입출금 Entity")
public class Pay extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 6792215552616441991L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "판매처ID(FK)")
    private Integer sellerId;

    @Schema(description = "판매처명")
    private String sellerNm;

    @Schema(description = "주문ID(FK)")
    private Integer orderId;

    @Schema(description = "전표번호")
    private Integer chitNo;

    @Schema(description = "입출구분")
    private String inoutCd;

    @Schema(description = "결제방법")
    private String payMethodCd;

    @Schema(description = "상태(판매구분)")
    private String custStatCd;

    @Schema(description = "입금일자")
    private LocalDate tranYmd;

    @Schema(description = "거래일시")
    private LocalDate workYmd;

    @Schema(description = "결제금액(고객이 결제하여야 하는 금액)")
    private BigDecimal payAmt;

    @Schema(description = "현금입금")
    private BigDecimal cashAmt;

    @Schema(description = "통장입금")
    private BigDecimal accountAmt;

    @Schema(description = "반품금액")
    private BigDecimal returnAmt;

    @Schema(description = "총금액")
    private BigDecimal totAmt;

    @Schema(description = "할인금액")
    private BigDecimal discountAmt;

    @Schema(description = "최초전잔")
    private BigDecimal firstBefAmt;

    @Schema(description = "전잔")
    private BigDecimal befAmt;

    @Schema(description = "비고")
    private String payEtc;

    @Schema(description = "비고인쇄여부")
    private String etcPrintYn;

    @Schema(description = "기존영업일")
    private LocalDate orgWorkYmd;

    @Schema(description = "befId")
    private Integer befId;

    @Schema(description = "변경구분 첫컬럼은 ")
    private String modCd;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}