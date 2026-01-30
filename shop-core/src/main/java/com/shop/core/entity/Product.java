package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.core.Local;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <pre>
 * Description: Product Entity
 * Author: park junsung
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Product", description = "상품 Entity")
public class Product extends BaseEntity implements Serializable {

//    @Serial
//    private static final long serialVersionUID = 3051513244958954521L; todo

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "상점(shop)Id(FK)")
    private Integer shopId;

    @Schema(description = "상품코드")
    private String prodCd;

    @Schema(description = "상품명")
    private String prodNm;

    @Schema(description = "상품유형")
    private Integer ProdTp;

    @Schema(description = "상품상세유형")
    private Integer ProdDetTp;

    @Schema(description = "구성")
    private String composition;

    @Schema(description = "이미지 파일 id")
    private Integer imgFileId;

    @Schema(description = "파일 id")
    private Integer fileId;

    @Schema(description = "제조 연월일")
    private LocalDate makeYmd;
}
