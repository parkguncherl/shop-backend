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
 * Description: OldSample Entity
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
@Schema(name = "OldSample", description = "샘플미회수 이력 Entity")
public class OldSample extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3056733344958954501L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "샘플일자")
    private LocalDate tranYmd;

    @Schema(description = "판매처명")
    private String sellerNm;

    @Schema(description = "전표번호")
    private Integer chitNo;

    @Schema(description = "품번")
    private String pumBun;

    @Schema(description = "품명")
    private String prodNm;

    @Schema(description = "칼라")
    private String color;

    @Schema(description = "사이즈")
    private String size;

    @Schema(description = "거래단가")
    private BigDecimal dealAmt;

    @Schema(description = "완료")
    private String compYn;

    @Schema(description = "샘플수량")
    private Integer sampleCnt;

    @Schema(description = "회수수량")
    private Integer collectCnt;

    @Schema(description = "잔량수량")
    private Integer remainCnt;

    @Schema(description = "잔량금액")
    private BigDecimal remainAmt;

    @Schema(description = "비고")
    private String etcCntn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}


