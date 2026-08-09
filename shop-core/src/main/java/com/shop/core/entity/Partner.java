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

    @Schema(description = "타입 셀러, 도매")
    private String partnerType;

    @Schema(description = "셀러명")
    private String partnerNm;

    @Schema(description = "보조 식별자(직접 매핑되지는 않으나 타 데이터에서 해당 partner 와의 관계를 표현하고자 사용 가능)")
    private String partnerTicker;

    @Schema(description = "셀러 하위명")
    private String partnerSubNm;

    @Schema(description = "위치")
    private String location;

    @Schema(description = "기타정보")
    private String etcInfo;

    @Schema(description = "서브도메인 www")
    private String subDomain;

    @Schema(description = "전화번호")
    private String phoneNo;

    @Schema(description = "대표자명")
    private String repNm;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "첫인사 메시지")
    private String firstGreetingMessage;

    @Schema(description = "리뷰 포인트 적립률 % 단위 즉 2 이면 2%")
    private Integer reviewPointRate;

    @Schema(description = "사이즈 정보 (콤마 구분, 예: 66,77)")
    private String sizeInfo;

    @Schema(description = "AI 기본 학습 텍스트 (고정 안내 역할 등)")
    private String aiStudyText;

    @Schema(description = "AI 상품 상세 학습 텍스트 (상품 상담 전 스터디용)")
    private String aiStudyProdDetailText;

    @Schema(description = "파트너 이미지(파일 저장소 폴더 프리픽스로 사용)")
    private String partnerImage;

    @Schema(description = "카카오 스토리 ID")
    private String kakaoStoryId;

    @Schema(description = "카카오 ID")
    private String kakaoId;

    @Schema(description = "인스타그램 ID")
    private String instaId;
}
