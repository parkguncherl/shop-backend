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

/** 스큐 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "스큐 Entity")
public class Sku extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8572972194466663551L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "스큐코드")
    private String skuCd;

    @Schema(description = "상품ID(FK)")
    private Integer prodId;

    @Schema(description = "스큐명")
    private String skuNm;

    @Schema(description = "스큐초성명")
    private String skuSimpleNm;

    @Schema(description = "사이즈")
    private String skuSize;

    @Schema(description = "색상")
    private String skuColor;

    @Schema(description = "표준색상코드")
    private String stdColorCd;

    @Schema(description = "판매가")
    private BigDecimal sellAmt;

    @Schema(description = "기준가")
    private BigDecimal stdSellAmt;

    @Schema(description = "마진율")
    private String marRate;

    @Schema(description = "원가")
    private BigDecimal orgAmt;

    @Schema(description = "파일ID(FK)")
    private Integer fileId;

    @Schema(description = "이미지파일ID(FK)")
    private Integer imgFileId;

    @Schema(description = "재고건수")
    private Integer invenCnt;

    @Schema(description = "휴면여부")
    private String sleepYn;

    /* 신규추가 시작 */
    @Schema(description = "디자이너id")
    private Integer designId;

    @Schema(description = "상품속성(제작여부)")
    private String prodAttrCd;

    @Schema(description = "구분내용")
    private String gubunCntn;

    @Schema(description = "혼용율")
    private String compCntn;

    @Schema(description = "기능분류")
    private String funcCd;

    @Schema(description = "기능세분류")
    private String funcDetCd;

    @Schema(description = "시즌")
    private String seasonCd;

    @Schema(description = "소매 id(제작인경우)")
    private Integer sellerId;

    @Schema(description = "출시년월일")
    private LocalDate releaseYmd;

    @Schema(description = "최소발주수량")
    private Integer minAsnCnt;

    @Schema(description = "원단코드")
    private String fabric;

    @Schema(description = "디자이너명")
    private String designNm;

    @Schema(description = "입고가")
    private BigDecimal inAmt;

    @Schema(description = "소매가")
    private BigDecimal retailAmt;

    @Schema(description = "비고")
    private String skuCntn;

    /* 신규추가 끝 */

    /* 신규추가 시작 */
    @Schema(description = "내부바코드")
    private String inBarCode;

    @Schema(description = "외부바코드")
    private String extBarCode;

    @Schema(description = "요척")
    private String yochug;

    @Schema(description = "이전 skuId")
    private String befId;

    @Schema(description = "maxStockId")
    private Integer maxStockId;

    @Schema(description = "max Job Det Id")
    private Integer maxJobDetId;

    @Schema(description = "센터재고")
    private Integer binblurCnt;

    @Schema(description = "매장건수")
    private Integer maejangCnt;

    @Schema(description = "샘플건수")
    private Integer sampleCnt;

    @Schema(description = "수선반출건수")
    private Integer takeOutCnt;

    @Schema(description = "샘플회수건수(이미매장건수에 들어감)")
    private Integer sampleReturnCnt;

    @Schema(description = "샘플건수")
    private Integer totInvenCnt;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}