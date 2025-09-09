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
 * Description: OldSale Entity
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
@Schema(name = "OldSale", description = "판매원장 이력 Entity")
public class OldSale extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3056125244958954501L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "거래일자")
    private LocalDate tranYmd;

    @Schema(description = "판매처명")
    private String sellerNm;

    @Schema(description = "전표번호")
    private Integer chitNo;

    @Schema(description = "구분")
    private String gubun;

    @Schema(description = "품번")
    private String pumBun;

    @Schema(description = "품명")
    private String prodNm;

    @Schema(description = "칼라")
    private String color;

    @Schema(description = "사이즈")
    private String size;

    @Schema(description = "사용자")
    private String userNm;

    @Schema(description = "입고가")
    private BigDecimal inAmt;

    @Schema(description = "거래단가")
    private BigDecimal dealAmt;

    @Schema(description = "단가DC")
    private BigDecimal danDc;

    @Schema(description = "판매량")
    private Integer saleCnt;

    @Schema(description = "판매금액")
    private BigDecimal saleAmt;

    @Schema(description = "반품량")
    private Integer returnCnt;

    @Schema(description = "반품금액")
    private BigDecimal returnAmt;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}


