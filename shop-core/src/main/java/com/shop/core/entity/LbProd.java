package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
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
@Schema(name = "LbProd", description = "라벨 상품 Entity")
public class LbProd extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4056713294921954591L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "라벨 파트너 ID")
    private String lbPartnerId;

    @Schema(description = "version")
    private String lbVersion;

    @Schema(description = "라벨 파트너 ID")
    private String prodId;

    @Schema(description = "라벨 파트너 명")
    private String lbPartnerNm;

    @Schema(description = "라방 타입 몬드(M)/빈블러(B)")
    private String lbType;

    @Schema(description = "라방 구분")
    private String lbGubun;

    @Schema(description = "원sku명")
    private String skuNm;

    @Schema(description = "판매상품명")
    private String prodNm;

    @Schema(description = "색상")
    private String color;

    @Schema(description = "영문색상")
    private String engColor;

    @Schema(description = "사이즈")
    private String size;

    @Schema(description = "도매가격")
    private Integer domaeAmt;

    @Schema(description = "판매가격")
    private Integer sellAmt;

    @Schema(description = "최소판매가격")
    private Integer minSellAmt;

    @Schema(description = "판매 수량")
    private Integer skuCnt;

    @Schema(description = "판매수량")
    private Integer sellerSellCnt;

    @Schema(description = "생성자")
    private String creUser;

    @Schema(description = "비고")
    private String etc;

    @Schema(description = "생성시간")
    private LocalDateTime creTm;

    @Schema(description = "수정자")
    private String updUser;

    @Schema(description = "수정시간")
    private LocalDateTime updTm;

    @Schema(description = "삭제여부 (Y/N)")
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
