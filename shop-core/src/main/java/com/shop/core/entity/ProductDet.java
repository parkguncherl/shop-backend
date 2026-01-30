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

    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    @Schema(description = "파트너ID(FK)")
    private Integer partnerDetSeq;

    @Schema(description = "파트너ID(FK)")
    private String productDetSize;

    @Schema(description = "파트너ID(FK)")
    private String productDetColor;

    @Schema(description = "원가")
    private BigDecimal orgAmt;

    @Schema(description = "판매가")
    private BigDecimal sellAmt;

    @Schema(description = "파트너ID(FK)")
    private Integer discountRate;

    @Schema(description = "파트너ID(FK)")
    private Integer fileId;

    @Schema(description = "파트너ID(FK)")
    private String sleepYn;
}
