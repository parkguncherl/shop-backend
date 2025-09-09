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
@Schema(description = "sku공장 Entity")
public class SkuFactory extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4810305357251982306L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "공장ID(FK)")
    private Integer factoryId;

    @Schema(description = "스큐ID(FK)")
    private Integer skuId;

    @Schema(description = "메인여부")
    private String mainYn;

    @Schema(description = "단가")
    private Integer gagongAmt;

    @Schema(description = "비고")
    private String etcCntn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}