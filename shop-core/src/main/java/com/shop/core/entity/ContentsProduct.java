package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "연결상품 Entity")
public class ContentsProduct extends BaseEntity implements Serializable {

//    @Serial
//    private static final long serialVersionUID = -7404284196980959337L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "순서")
    private Integer seq;

    @Schema(description = "(상품)컨텐츠 id")
    private Integer contentsId;

    @Schema(description = "상품 id")
    private Integer productId;
}
