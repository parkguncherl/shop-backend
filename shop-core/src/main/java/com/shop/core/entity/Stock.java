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
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "입하 Entity")
public class Stock extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 6742022837847592902L;

    @Schema(description = "아이디")
    private Integer id;

    @Schema(description = "SKU_ID(FK)")
    private Integer skuId;

    @Schema(description = "LOGIS_ID(FK)")
    private Integer logisId;

    @Schema(description = "ASN_ID(FK)")
    private Integer asnId;

    @Schema(description = "PARTNER_ID(FK)")
    private Integer partnerId;

    @Schema(description = "입하구분")
    private String stockCd;

    @Schema(description = "입하일")
    private LocalDate stockYmd;

    @Schema(description = "입하수량")
    private Integer stockCnt;

    @Schema(description = "입하상태")
    private String stockStatCd;

    @Schema(description = "입하사유")
    private String stockRsnCd;

    @Schema(description = "입출금처리여부(입하테이블로 입출금을 만들면 Y로 변경)")
    private String tranYn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}