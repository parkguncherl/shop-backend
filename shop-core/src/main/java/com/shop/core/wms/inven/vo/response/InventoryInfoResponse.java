package com.shop.core.wms.inven.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

public class InventoryInfoResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InventoryinfoResponsePaging", description = "재고 정보 조회 (페이징) 응답", type = "object")
    public static class Paging {
        @Schema(description = "전체 행 수")
        private Integer totalRowCount;

        @Schema(description = "행 번호")
        private Integer no;

        @Schema(description = "대기건수")
        private Integer waitingCnt;

        @Schema(description = "ID")
        private Integer id;

        @Schema(description = "LOCATION ID")
        private Integer locId;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "PARTNER ID")
        private Integer partnerId;

        @Schema(description = "고객사")
        private String partnerNm;

        @Schema(description = "skuNm")
        private String skuNm;

        @Schema(description = "기능분류")
        private String funcCd;

        @Schema(description = "기능세분류")
        private String funcDetCd;

        @Schema(description = "시즌")
        private String season;

        @Schema(description = "zone")
        private String zoneDesc;

        @Schema(description = "zoneCdNm")
        private String zoneCdNm;

        @Schema(description = "lctn")
        private String locAlias;

        @Schema(description = "창고 재고")
        private Integer centerCnt;

        @Schema(description = "매장재고")
        private Integer retailCnt;

        @Schema(description = "주간출고건수")
        private Integer weekOutCnt;

        @Schema(description = "월간출고건수")
        private Integer monthOutCnt;

        @Schema(description = "최근입고일")
        private String stockYmd;

        @Schema(description = "최근입고수량")
        private Integer stockCnt;

        @Schema(description = "최근출고일")
        private String jobOutYmd;

        @Schema(description = "최근출고수량")
        private Integer jobCnt;

        @Schema(description = "이미지파일ID(FK)")
        private Integer imgFileId;

        // 트리 관련 추가
/*
        @Schema(description = "path번호")
        private List<String> path;

        @Schema(description = "level")
        private Integer level;

        @Schema(description = "rank")
        private Integer rank;
*/


        // 변경을 위한 컬럼들
        @Schema(description = "toZone")
        private Integer toZone;

        @Schema(description = "toLoc")
        private Integer toLoc;

        @Schema(description = "chgCnt")
        private Integer chgCnt;

    }

    @Getter
    @Setter
    @Schema(description = "디테일 정보 조회 응답")
    public static class Detail {
        @Schema(description = "행 번호")
        private Long no;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "ZONE 코드명")
        private String zoneCdNm;

        @Schema(description = "로케이션명")
        private String location;

        @Schema(description = "로케이션 ID")
        private Integer locId;

        @Schema(description = "로케이션 상세")
        private String locCntn;

        @Schema(description = "화주명")
        private String partnerNm;

        @Schema(description = "물류 재고 수량")
        private Integer inventoryAmt;

        @Schema(description = "로케이션별 재고수량")
        private Integer invenCnt;

        @Schema(description = "창고 ID")
        private Integer logisId;

        @Schema(description = "창고명")
        private String logisNm;

        @Schema(description = "재고 상태 코드")
        private String invenStatCd;

        @Schema(description = "재고 일자")
        private LocalDate invenYmd;

        @Schema(description = "SKU 코드")
        private String skuCd;

        @Schema(description = "SKU 명")
        private String skuNm;

        @Schema(description = "SKU 색상")
        private String skuColor;

        @Schema(description = "SKU 사이즈")
        private String skuSize;

        @Schema(description = "판매 금액")
        private BigDecimal sellAmt;

        @Schema(description = "기준 판매 금액")
        private BigDecimal stdSellAmt;

        @Schema(description = "마진율")
        private String marRate;

        @Schema(description = "최소발주수량")
        private String minasnCnt;

        @Schema(description = "원가")
        private BigDecimal orgAmt;

        @Schema(description = "휴면 여부")
        private String sleepYn;

        @Schema(description = "상품 ID")
        private Integer prodId;

        @Schema(description = "상품 코드")
        private String prodCd;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "상품 유형")
        private String prodType;

        @Schema(description = "기능 코드")
        private String funcCd;

        @Schema(description = "상세 기능 코드")
        private String funcDetCd;

        @Schema(description = "시즌 코드")
        private String seasonCd;

        @Schema(description = "원단")
        private String fabric;

        @Schema(description = "형태 여부")
        private String formYn;

        @Schema(description = "혼용율 내용")
        private String compCntn;

        @Schema(description = "구분 내용")
        private String gubunCntn;

        @Schema(description = "주문 약칭")
        private String orderShortNm;

        @Schema(description = "구역 코드")
        private String zoneCd;

        @Schema(description = "공장명")
        private String factoryNm;

        @Schema(description = "디자이너명")
        private String designNm;

        @Schema(description = "SKU품목수")
        private Integer skuAmt;

        @Schema(description = "화주 재고 수량")
        private Integer partnerInventoryAmt;

        @Schema(description = "화주 아이디")
        private Integer partnerId;

        /** 등록일시 */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        @Schema(description = "등록일시")
        protected LocalDateTime creTm;

        /** 등록자 */
        @Schema(description = "등록자")
        protected String creUser;

        /** 수정일시 */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        @Schema(description = "수정일시")
        protected LocalDateTime updTm;

        /** 수정자 */
        @Schema(description = "수정자")
        protected String updUser;
    }


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "InventoryLocationListResponse", description = "로케이션별 재고 정보 조회 응답", type = "object")
    public static class InventoryLocationListResponse {
        @Schema(description = "전체 행 수")
        private Integer totalRowCount;

        @Schema(description = "행 번호")
        private Integer no;

        @Schema(description = "LOCATION ID")
        private Integer locId;

        @Schema(description = "SKU ID")
        private Integer skuId;

        @Schema(description = "PARTNER ID")
        private Integer partnerId;

        @Schema(description = "고객사")
        private String partnerNm;

        @Schema(description = "zoneCdNm ")
        private String zoneCdNm;

        @Schema(description = "skuNm")
        private String skuNm;

        @Schema(description = "zone")
        private String zoneDesc;

        @Schema(description = "lctn")
        private String locAlias;

        @Schema(description = "창고 재고")
        private Integer centerCnt;

        @Schema(description = "재고 퍼센트")
        private BigDecimal skuCountPercent;

    }


    @Getter
    @Setter
    @Schema(name = "SkuLocationInfoListResponse", description = "디테일 정보 조회 응답")
    public static class SkuLocationInfo {
        @Schema(description = "행 번호")
        private Integer no;

        @Schema(description = "화주명")
        private String partnerNm;

        @Schema(description = "스큐명")
        private String skuNm;

        @Schema(description = "물류 재고 수량")
        private Integer totalInven;
    }
}