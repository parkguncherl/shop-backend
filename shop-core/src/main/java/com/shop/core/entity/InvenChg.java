// 재고 변경 상세 Entity
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
@Schema(description = "재고 변경 Entity")
public class InvenChg extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 97547949180999105L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "location id")
    private Integer locId;

    @Schema(description = "sku id")
    private Integer skuId;

    @Schema(description = "이전수량")
    private Integer befCnt;

    @Schema(description = "이후수량")
    private Integer aftCnt;

    @Schema(description = "변경 사유 10260 ")
    private String invenChgCd;

    @Schema(description = "job_det_id")
    private Integer JobDetId;

    @Schema(description = "inspect_det_id")
    private Integer inspectDetId;
}