package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.core.Local;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @Schema(description = "상점(partner)Id(FK)")
    private Integer partnerId;

    @Schema(description = "상품코드")
    private String prodCd;

    @Schema(description = "상품명")
    private String prodNm;

    @Schema(description = "원본상품명")
    private String orgProdNm;

    @Schema(description = "구성")
    private String composition;

    @Schema(description = "신상번호")
    private String sinsangNo;

    @Schema(description = "이미지 파일 id")
    private Integer repFileId;

    @Schema(description = "이미지 파일 id")
    private Integer detailFileId;

    @Schema(description = "이미지 파일 id")
    private Integer sizeFileId;

    @Schema(description = "이미지 파일 id")
    private Integer etcFileId;

    @Schema(description = "제조 연월일")
    private LocalDate makeYmd;

    @Schema(description = "원가")
    private BigDecimal orgAmt;

    @Schema(description = "판매가")
    private BigDecimal sellAmt;

    @Schema(description = "할인율")
    private BigDecimal discountRate;

    @Schema(description = "춘계 의상 여부")
    private String isSpring;

    @Schema(description = "하계 의상 여부")
    private String isSummer;

    @Schema(description = "추계 의상 여부")
    private String isAutumn;

    @Schema(description = "동계 의상 여부")
    private String isWinter;

    @Schema(description = "선호도 점수 인기정렬의 기준")
    private Integer favoritePoint;

    @Schema(description = "협력업체 id")
    private Integer vendorId;

    @Schema(description = "전시여부")
    private String showYn;

    @Schema(description = "두께 타입 코드")
    private String thickTp;

    @Schema(description = "신축성 타입 코드")
    private String spanTp;

    @Schema(description = "비침 타입 코드")
    private String showTp;

    @Schema(description = "세탁 타입 코드")
    private String laundryTp;

    @Schema(description = "비치는정도 타입 코드")
    private String transTp;

    @Schema(description = "상품 상세 설명")
    private String detInfo;
}
