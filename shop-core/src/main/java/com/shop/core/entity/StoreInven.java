package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <pre>
 * Description: StoreInven Entity
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
@Schema(name = "StoreInven", description = "매장재고(조사) Entity")
public class StoreInven extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 9192648602254023071L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "처리일시")
    private LocalDateTime tranTm;

    @Schema(description = "스큐ID(FK)")
    private Integer skuId;

    @Schema(description = "조사전 건수")
    private Integer skuCnt;

    @Schema(description = "조사후 건수")
    private Integer realCnt;

    @Schema(description = "신규추가여부")
    private String newYn;

    @Schema(description = "비고")
    private String etcCntn;
}
