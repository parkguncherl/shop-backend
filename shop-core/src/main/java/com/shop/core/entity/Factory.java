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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** 공장 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "공장 Entity")
public class Factory extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -8537429094890442307L;

    /** 아이디(PK) */
    @Schema(description = "아이디(PK)")
    private Integer id;

    /** 파트너ID(FK) */
    @Schema(description = "파트너ID(FK)")
    private Integer partnerId;

    /** 파트너_이름 */
    @Schema(description = "파트너_이름")
    private String partnerNm;

    /** 파트너_이름 */
    @Schema(description = "생산처_이름")
    private String busiNm;

    /** 공장종류코드 */
    @Schema(description = "공장종류코드")
    private String factoryCd;

    /** 회사명 */
    @Schema(description = "회사명")
    private String compNm;

    /** 회사_전화_번호 */
    @Schema(description = "회사_전화_번호")
    private String compTelNo;

    /** 담당자_명 */
    @Schema(description = "담당자_명")
    private String personNm;

    /** 담당자_전화_번호 */
    @Schema(description = "담당자_전화_번호")
    private String personTelNo;

    /** 화물_명 */
    @Schema(description = "화물_명")
    private String senderNm;

    /** 화물_전화_번호 */
    @Schema(description = "화물_전화_번호")
    private String senderTelNo;

    /** 사업자_번호 */
    @Schema(description = "사업자_번호")
    private String compNo;

    /** 상세_정보 */
    @Schema(description = "상세_정보")
    private String detailInfo;

    /** 회사이메일 */
    @Schema(description = "회사이메일")
    private String compEmail;

    @Schema(description = "회사fax")
    private String compFaxNo;

    @Schema(description = "회사주소")
    private String compAddr;

    /** file ID */
    @Schema(description = "file ID")
    private Integer fileId;

    /** sns종류(명) */
    @Schema(description = "sns종류(명)")
    private String snsType;

    /** sns ID */
    @Schema(description = "sns ID")
    private String snsId;

    @Schema(description = "처리확인")
    private String tranYn;

    @Schema(description = "잔액인쇄여부")
    private String remPrnYn;

    @Schema(description = "비고(화면)")
    private String etcScrCntn;

    @Schema(description = "비고(전표)")
    private String etcChitCntn;

    @Schema(description = "공장현잔")
    private BigDecimal nowAmt;

    @Schema(description = "구분1")
    private String gubun1;

    @Schema(description = "구분2")
    private String gubun2;

    @Schema(description = "대표자명")
    private String ceoNm;

    @Schema(description = "대표자전화번호")
    private String ceoTelNo;

    @Schema(description = "휴면여부")
    private String sleepYn;

    @Schema(description = "잔액인쇄여부")
    private String remainYn;

    @Schema(description = "등록일자")
    private LocalDate regYmd;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}