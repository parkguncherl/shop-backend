package com.shop.core.entity;

import com.shop.core.entity.adapter.LocalDateTimeSerializer;
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

/** 주문상세 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "주문상세 Entity")
public class OrderDet extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3864686720056556213L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "오더ID(FK)")
    private Integer orderId;

    @Schema(description = "SKU_ID(FK)")
    private Integer skuId;

    @Schema(description = "SKU명")
    private String skuNm;

    @Schema(description = "주문 순서")
    private Integer orderSeq;

    @Schema(description = "상세전표번호")
    private String detChitNo;

    @Schema(description = "주문상세코드")
    private String orderDetCd;

    @Schema(description = "실제스큐금액(할인젹용)")
    private BigDecimal realAmt;

    @Schema(description = "단가")
    private BigDecimal baseAmt;

    @Schema(description = "금액")
    private BigDecimal totAmt;

    @Schema(description = "단가DC")
    private BigDecimal dcAmt;

    @Schema(description = "수량")
    private Integer skuCnt;

    @Schema(description = "주문상세비고(주로 샘플에서 사용)")
    private String orderDetEtc;

    @Schema(description = "완료여부(job 처리가 완료된경우 주로 미송에서 사용)")
    private String finishYn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}