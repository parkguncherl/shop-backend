package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <pre>
 * Description: 셀러 Entity
 * Date: 2026/03/17
 * Author: park junsung
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Partner", description = "계정 Entity")
public class Partner extends BaseEntity implements Serializable {

    @Schema(description = "아이디")
    private Integer id;

    @Schema(description = "셀러명")
    private String partnerNm;

    @Schema(description = "보조 식별자(직접 매핑되지는 않으나 타 데이터에서 해당 partner 와의 관계를 표현하고자 사용 가능)")
    private String partnerTicker;

    @Schema(description = "셀러 하위명")
    private String partnerSubNm;

    @Schema(description = "주소(도메인)")
    private String domain;

    @Schema(description = "전화번호")
    private String phoneNo;
}
