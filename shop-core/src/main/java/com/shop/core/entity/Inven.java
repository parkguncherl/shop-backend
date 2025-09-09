package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "재고 Entity")
public class Inven extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 97547947770999105L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "입하ID")
    private Integer stockId;

    @Schema(description = "스큐ID")
    private Integer skuId;

    @Schema(description = "파트너ID")
    private Integer partnerId;

    @Schema(description = "창고ID")
    private Integer logisId;

    @Schema(description = "위치ID")
    private Integer locId;

    @Schema(description = "재고상태")
    private String invenStatCd;

    @Schema(description = "입고일")
    private LocalDate invenYmd;

    @Schema(description = "작업상세ID")
    private Integer jobDetId;

    @Schema(description = "매장재고로 생성된id")
    private Integer storeInvenId;

    @Schema(description = "재고변경id")
    private Integer invenChgId;

}
