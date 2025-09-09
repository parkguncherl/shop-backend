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

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "작업 Entity")
public class JobDet extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8552753517112876015L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "job ID(FK)")
    private Integer jobId;

    @Schema(description = "order ID(FK)")
    private Integer orderId;

    @Schema(description = "주문상세ID(FK)")
    private Integer orderDetId;

    @Schema(description = "스큐아이디ID(FK)")
    private Integer skuId;

    @Schema(description = "작업대상수량")
    private Integer jobCnt;

    @Schema(description = "작업수량")
    private Integer realCnt;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}