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

/**
 * <pre>
 * Description: 라벨 상품 Entity
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
@Schema(name = "LbSelling", description = "라벨 상품 Entity")
public class LbVersionSeller extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3056731944911954152L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "version ID")
    private String lbVersion;

    @Schema(description = "라벨 seller ID")
    private Integer lbSellerId;

    @Schema(description = "방송일")
    private LocalDate lbDate;

    @Schema(description = "방송시간")
    private LocalTime lbTime;

    @Schema(description = "비고")
    private String sellerEtc;

    @Schema(description = "상세")
    private String lbDetail;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
