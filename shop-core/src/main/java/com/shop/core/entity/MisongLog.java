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
 * Description: misong log Entity
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
@Schema(name = "MisongLog", description = "미송로그 Entity")
public class MisongLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3051333244958954521L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "주문상세id(FK)")
    private Integer orderDetId;

    @Schema(description = "미송건수")
    private Integer misongCnt;

    @Schema(description = "발송건수")
    private Integer sendCnt;

    @Schema(description = "잔량건수")
    private Integer remainCnt;

    @Schema(description = "묶음여부")
    private String bundleYn;

    @Schema(description = "삭제여부")
    private String delYn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}


