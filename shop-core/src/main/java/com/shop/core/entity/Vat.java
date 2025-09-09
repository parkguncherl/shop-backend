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
 * Description: vat Entity
 * Company: binblur
 * </pre>
 */

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "부가세 Entity")
public class Vat extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 7768324815430910709L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "판매처ID(FK)")
    private Integer sellerId;

    @Schema(description = "영업일자")
    private LocalDate workYmd;

    @Schema(description = "대상기간시작")
    private LocalDate vatStrYmd;

    @Schema(description = "대상기간종료")
    private LocalDate vatEndYmd;

    @Schema(description = "(청구된) vat 금액")
    private BigDecimal vatAmt;

    @Schema(description = "발행여부")
    private String issuYn;

    @Schema(description = "비고")
    private String etcCntn;

    @Schema(description = "비고출력여부")
    private String etcPrnYn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}