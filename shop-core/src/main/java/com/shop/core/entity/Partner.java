package com.shop.core.entity;

import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "파트너 Entity")
public class Partner extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1291947095425309803L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "상위파트너ID(FK)")
    private Integer upperPartnerId;

    @Schema(description = "파일ID(FK)")
    private Integer fileId;

    @Schema(description = "창고ID(FK)")
    private Integer logisId;

    @Schema(description = "회사명")
    private String partnerNm;

    @Schema(description = "회사영문명")
    private String partnerEngNm;

    @Schema(description = "약어")
    private String shortNm;

    @Schema(description = "회사_전화_번호")
    private String partnerTelNo;

    @Schema(description = "대표자_명")
    private String repNm;

    @Schema(description = "대표자_전화_번호")
    private String repTelNo;

    @Schema(description = "사업자_번호")
    private String compNo;

    @Schema(description = "상세_정보")
    private String detailInfo;

    @Schema(description = "계좌비고(영수증하단)")
    private String etcAccCntn;

    @Schema(description = "추가시간")
    private Integer addTime;

    @Schema(description = "회사이메일")
    private String partnerEmail;

    @Schema(description = "미송승인후배송여부")
    private String misongYn;

    @Schema(description = "사이즈정보")
    private String sizeInfo;

    @Schema(description = "제작단축명")
    private String orderShortNm;

    @Schema(description = "혼용율정보")
    private String compInfo;

    @Schema(description = "구분1정보")
    private String gubunInfo;

    @Schema(description = "정산코드")
    private String settleCd;

    @Schema(description = "혼용율인쇄코드")
    private String compPrnCd;

    @Schema(description = "샘플시 전체 스큐정보 인쇄여부(샘플동일상품출력여부)")
    private String samplePrnYn;

    @Schema(description = "생산처 구분1 내용")
    private String facGb1Cntn;

    @Schema(description = "생산처 구분2 내용")
    private String facGb2Cntn;

    @Schema(description = "판매처 구분1 내용")
    private String selGb1Cntn;

    @Schema(description = "판매처 구분2 내용")
    private String selGb2Cntn;

    @Schema(description = "관리디자이너명")
    private String designCntn;

    @Schema(description = "주소")
    private String partnerAddr;

    @Schema(description = "주소기타")
    private String partnerAddrEtc;

    @Schema(description = "snsid")
    private String snsId;

    @Schema(description = "snsType")
    private String snsType;

    @Schema(description = "기타")
    private String etcCntn;

    @Schema(description = "정산코드")
    private String settlCd;

    @Schema(description = "생산처결제 비고")
    private String facsettleEtcCntn;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}