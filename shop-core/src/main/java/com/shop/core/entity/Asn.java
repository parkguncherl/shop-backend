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
@Schema(description = "발주 Entity")
public class Asn extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2804836588706371345L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "LOGIS_ID(FK)")
    private Integer logisId;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "공장ID(FK)")
    private Integer factoryId;

    @Schema(description = "스큐ID(FK)")
    private Integer skuId;

    @Schema(description = "스큐별 단가")
    private Integer skuDanAmt;

    @Schema(description = "발주유형")
    private String asnType;

    @Schema(description = "발주단가DC")
    private BigDecimal dcAmt;

    @Schema(description = "전표번호")
    private Integer chitNo;

    @Schema(description = "신청일")
    private LocalDate workYmd;

    @Schema(description = "원래 신청일")
    private LocalDate orgWorkYmd;

    @Schema(description = "출고일")
    private LocalDate outYmd;

    @Schema(description = "발주수량")
    private Integer genCnt;

    @Schema(description = "ASN수량")
    private Integer asnCnt;

    @Schema(description = "asn 출처")
    private String asnOrigin;

    @Schema(description = "발주유형코드")
    private String asnStatCd;

    @Schema(description = "원단처")
    private String fabricComp;

    @Schema(description = "재단장수")
    private String cutCntn;

    @Schema(description = "디자이너명")
    private String designNm;

    @Schema(description = "비고")
    private String asnEtc;

    @Schema(description = "참조아이디")
    private Integer parentId;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}