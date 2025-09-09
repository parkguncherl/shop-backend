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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** 상품 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "상품 Entity")
public class DailyInven extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4710305357251112306L;
    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "작업일자")
    private LocalDate workYmd;

    @Schema(description = "SKU ID")
    private Integer skuId;

    @Schema(description = "센터 수량")
    private Integer centerCnt;

    @Schema(description = "리테일 수량")
    private Integer retailCnt;

    @Schema(description = "샘플 수량")
    private Integer sampleCnt;

    @Schema(description = "반품 수량")
    private Integer returnCnt;

    @Schema(description = "전체 수량")
    private Integer totCnt;

    @Schema(description = "샘플 반품 수량")
    private Integer sampleReturnCnt;

    @Schema(description = "이전 전체 수량")
    private Integer befTotCnt;

    @Schema(description = "생성 시간")
    private LocalDateTime creTm;


    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}