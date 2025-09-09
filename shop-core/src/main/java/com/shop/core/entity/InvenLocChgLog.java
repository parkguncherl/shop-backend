package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로케이션 변경 이력 Entity")
public class InvenLocChgLog extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 6046570005117055737L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "스큐 ID")
    private Long skuId;

    @Schema(description = "이전 로케이션")
    private String befLoc;

    @Schema(description = "이후 로케이션")
    private String aftLoc;

    @Schema(description = "변경 후 수량")
    private Integer aftCnt;

    @Schema(description = "재고 변경 상태 코드")
    private String invenChgCd;

    @Schema(description = "변경 비고")
    private String chgEtc;
}