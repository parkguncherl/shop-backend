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

/** 발주상세 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "발주상세 Entity")
public class AsnDet extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7844764580241059874L; // 예시 값입니다. 실제 값은 다를 수 있습니다.

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "스큐ID(FK)")
    private Integer skuId;

    @Schema(description = "발주ID(FK)")
    private Integer asnId;

    @Schema(description = "발주상세코드")
    private String asnDetCd;

    @Schema(description = "출고일")
    private LocalDate outYmd;

    @Schema(description = "발주수량")
    private Integer genCnt;

    @Schema(description = "ASN수량")
    private Integer asnCnt;

    @Schema(description = "실수량")
    private Integer realCnt;

    @Schema(description = "미처리수량")
    private Integer nonCnt;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}