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

/** 공장 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "공장입출금 Entity")
public class FactoryInout extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -8536429091895442307L;

    /** 아이디(PK) */
    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "스큐 ID")
    private Integer skuId;

    @Schema(description = "ASN ID")
    private Integer asnId;

    @Schema(description = "stock ID")
    private Integer stockId;

    @Schema(description = "공장 ID")
    private Integer factoryId;

    @Schema(description = "전표 번호")
    private Integer chitNo;

    @Schema(description = "입출고 유형")
    private String dwTp;

    @Schema(description = "거래 일자")
    private LocalDate tranYmd;

    @Schema(description = "작업 일자")
    private LocalDate workYmd;

    @Schema(description = "현금 금액")
    private BigDecimal cashAmt;

    @Schema(description = "계좌 금액")
    private BigDecimal accountAmt;

    @Schema(description = "할인 금액")
    private BigDecimal dcAmt;

    @Schema(description = "대상총건수")
    private Integer totCnt;

    @Schema(description = "총 금액")
    private BigDecimal totAmt;
    
    @Schema(description = "전잔 금액")
    private BigDecimal befAmt;

    @Schema(description = "반출 단가DC")
    private BigDecimal outDcAmt;

    @Schema(description = "기타 내용")
    private String etcCntn;

    @Schema(description = "비고 인쇄 여부")
    private String etcPrintYn;

    @Schema(description = "이전 ID")
    private Integer befId;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}