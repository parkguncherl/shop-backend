package com.shop.core.entity;

import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.enums.BooleanValueCode;
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
@Schema(name = "StoreReq", description = "매장요청 Entity")
public class StoreReq extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 9192648601754023071L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "샘플jobDetID(FK)")
    private Integer sampleJobDetId;

    @Schema(description = "스큐ID(FK)")
    private Integer skuId;

    @Schema(description = "영업일(작업일)")
    private LocalDate workYmd;

    @Schema(description = "처리일시")
    private LocalDateTime tranTm;

    @Schema(description = "작업상세ID(FK)")
    private Integer jobDetId;

    @Schema(description = "스큐수량")
    private Integer skuCnt;

    @Schema(description = "발주id(FK)")
    private Integer asnId;

    @Schema(description = "매장요청코드")
    private String storeReqCd;

    @Schema(description = "매장요청상태")
    private String reqStatCd;

    @Schema(description = "반납사유")
    private String etcCntn;

    @Schema(description = "반납확정 비고")
    private String conCntn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}