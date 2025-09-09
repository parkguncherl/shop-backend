package com.shop.core.entity;

import com.google.gson.GsonBuilder;
import com.shop.core.entity.adapter.LocalDateSerializer;
import com.shop.core.entity.adapter.LocalDateTimeSerializer;
import com.shop.core.entity.adapter.LocalTimeSerializer;
import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <pre>
 * Description: seller Entity
 * Company: home
 * </pre>
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Seller", description = "판매자 Entity")
public class Seller extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5090586754425567694L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "이력참조키")
    private Integer befId;

    @Schema(description = "파트너 Id")
    private Integer partnerId;

    @Schema(description = "판매자명")
    private String sellerNm;

    @Schema(description = "판매자팩스번호")
    private String sellerFaxNo;

    @Schema(description = "판매자주소")
    private String sellerAddr;

    @Schema(description = "회사전화번호")
    private String sellerTelNo;

    @Schema(description = "대표자명")
    private String ceoNm;

    @Schema(description = "담당자명")
    private String personNm;

    @Schema(description = "담당자전화번호")
    private String personTelNo;

    @Schema(description = "대표연락처")
    private String ceoTelNo;

    @Schema(description = "사업자번호")
    private String compNo;

    @Schema(description = "사업자명")
    private String compNm;

    @Schema(description = "비고(화면)")
    private String etcScrCntn;

    @Schema(description = "비고(전표)")
    private String etcChitCntn;

    @Schema(description = "계좌(전표)")
    private String etcAccCntn;

    @Schema(description = "업태")
    private String busiTypeNm;

    @Schema(description = "종목")
    private String busiSectNm;

    @Schema(description = "영업일")
    private String workingDay;

    @Schema(description = "결제대행업체")
    private String payAgency;

    @Schema(description = "회사이메일")
    private String compEmail;

    @Schema(description = "휴면여부")
    private String sleepYn;

    @Schema(description = "혼용율인쇄코드")
    private String compPrnCd;

    @Schema(description = "샘플상품전체인쇄여부")
    private String samplePrnYn;

    @Schema(description = "잔액인쇄YN")
    private String remainYn;

    @Schema(description = "처리확인YN")
    private String treatYn;

    @Schema(description = "계산서YN")
    private String billYn;

    @Schema(description = "부가세YN")
    private String vatYn;

    @Schema(description = "금액상한")
    private Integer limitAmt;

    @Schema(description = "현매입액")
    private Integer purchaseAmt;

    @Schema(description = "VAT잔액")
    private Integer vatNowAmt;

    @Schema(description = "현금잔액")
    private Integer nowAmt;

    @Schema(description = "파일ID")
    private Integer fileId;

    @Schema(description = "결제주기정보")
    private String payCylInfo;

    @Schema(description = "구분1")
    private String gubun1;

    @Schema(description = "구분2")
    private String gubun2;

    @Schema(description = "등록일자")
    private LocalDate regYmd;

    @Schema(description = "SNS ID")
    private String snsId;

    @Schema(description = "비고")
    private String etcCntn;

    @Schema(description = "사입자명")
    private String saibNm;

    @Schema(description = "사입자연락처")
    private String saibTelNo;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}