package com.shop.core.wms.ipgo.vo.response;

import com.shop.core.interfaces.PageBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * 발주 응답 클래스
 */
public class AsnResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "AsnResponsePaging", description = "재고확정 페이징 응답")
    public static class Paging extends PageBaseEntity {

        @Schema(description = "전체 행 수")
        private Integer totalRowCount;

        @Schema(description = "행 번호")
        private Integer no;

        @Schema(description = "고객ID")
        private Integer partnerId;

        @Schema(description = "생산처ID")
        private Integer factoryId;

        @Schema(description = "고객명")
        private String partnerNm;

        @Schema(description = "생산처")
        private String factoryNm;

        @Schema(description = "발주유형코드")
        private String asnTypeCd;

        @Schema(description = "발주유형명")
        private String asnTypeNm;

        @Schema(description = "발주일")
        private LocalDate asnWorkYmd;

        @Schema(description = "경과일")
        private Integer asnPeriod;

        @Schema(description = "품목종류 수량")
        private Integer prodCnt;

        @Schema(description = "SKU종류 수량")
        private Integer skuCnt;

        @Schema(description = "총 발주수량")
        private Integer totalGenCnt;

        @Schema(description = "기입고수량")
        private Integer befInstockCnt;

//        @Schema(description = "발주ID 배열")
//        private String asnIds;
//
//        public List<Integer> getAsnIds() {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                if (StringUtils.isNotEmpty(asnIds)) {
//                    return objectMapper.readValue(asnIds, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
//                } else {
//                    return null;
//                }
//
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "AsnResponseStatDashBoard", description = "발주 통계 대시보드")
    public static class StatDashBoard {

        @Schema(description = "전체 발주량")
        private Integer totalGenCnt;

        @Schema(description = "총 고객 수")
        private Integer totalPartnerCnt;

        @Schema(description = "최대 발주량 고객명")
        private String maxAsnPartnerNm;

        @Schema(description = "최대 발주량")
        private Integer maxGenCnt;
    }

    @Getter
    @Setter
    @Schema(name = "AsnResponseDetail", description = "발주 상세품목 응답")
    public static class Detail {

        @Schema(description = "No")
        private Integer no;

        @Schema(description = "발주_ID")
        private Integer asnId;

        @Schema(description = "고객명")
        private String partnerNm;

        @Schema(description = "생산처")
        private String factoryNm;

        @Schema(description = "스큐명")
        private String skuNm;

        @Schema(description = "발주수량")
        private Integer genCnt;

        @Schema(description = "기입고 수량")
        private Integer befInstockCnt;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "AsnResponsePrintDetail", description = "발주 전표 상세 응답")
    public static class PrintDetail {

        @Schema(description = "상세List")
        private List<AsnResponse.DetailItem> detailItems;

        @Schema(description = "작업상태")
        private String jobStat;

        @Schema(description = "고객사명")
        private String partnerNm;

        @Schema(description = "생산처명")
        private String factoryNm;

        @Schema(description = "발주일")
        private String workYmd;

        @Schema(description = "발주구분")
        private String asnType;

        @Schema(description = "수량합계")
        private Integer totCnt;

        @Schema(description = "품목종류 수량")
        private Integer prodCnt;

//        @Schema(description = "금액합계")
//        private BigDecimal totAmt;

        @Schema(description = "전표출력자")
        private String printUser;
    }

    @Getter
    @Setter
    @Schema(name = "AsnResponseDetailItem", description = "발주 전표 상세품목 응답")
    public static class DetailItem {

        @Schema(description = "품목명")
        private String skuNm;

        @Schema(description = "수량")
        private Integer quantity;

//        @Schema(description = "단가")
//        private BigDecimal unitPrice;
//
//        @Schema(description = "금액")
//        private BigDecimal amount;
    }
}