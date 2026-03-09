package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * Description: ProductDet Entity
 * Author: park junsung
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Order", description = "주문 Entity")
public class ProductDet extends BaseEntity implements Serializable {

    //    @Serial
//    private static final long serialVersionUID = 3051513244958954521L; todo

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "prod id")
    private Integer prodId;

    @Schema(description = "상품상세 순서(seq, 이를 통해 prod 이하에서 고유 요소 식별)")
    private Integer prodDetSeq;

    @Schema(description = "상품상세 사이즈")
    private String prodDetSize;

    @Schema(description = "상품상세 컬러")
    private String prodDetColor;

    @Schema(description = "스큐 할인율")
    private Integer skuDiscountRate;

    @Schema(description = "file id")
    private Integer fileId;

    @Schema(description = "휴면여부(Y/N)")
    private String sleepYn;
}
