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
@Schema(description = "발주 그룹 Entity")
public class AsnGroup extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 975479477709991031L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "asn 그룹 key(uuid)")
    private String asnGroup;

    @Schema(description = "발주ID")
    private Integer asnId;
}
