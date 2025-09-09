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

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "파트너 Entity")
public class PartnerFee extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1291947095425391803L;
    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "적용 시작일")
    private LocalDate startDay;

    @Schema(description = "수수료 구분(1: 종류코드 등)")
    private String feeType;

    @Schema(description = "재고 수수료")
    private Integer stockFee;

    @Schema(description = "작업 수수료")
    private Integer jobFee;

    @Schema(description = "서비스 수수료")
    private BigDecimal serviceFee;

    @Schema(description = "관리 수수료")
    private BigDecimal maintFee;

    @Schema(description = "주문 수수료")
    private BigDecimal orderFee;

    @Schema(description = "행거 수수료")
    private BigDecimal hangerFee;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}