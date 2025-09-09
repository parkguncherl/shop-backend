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
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "작업 Entity")
public class Michul extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8242753517112876012L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "영업일")
    private LocalDate workYmd;

    @Schema(description = "seller ID(FK)")
    private Integer sellerId;

    @Schema(description = "partnerId ID(FK)")
    private Integer partnerId;

    @Schema(description = "sku ID(FK)")
    private Integer skuId;

    @Schema(description = "수량")
    private Integer skuCnt;

    @Schema(description = "미출기타")
    private String michulEtc;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}