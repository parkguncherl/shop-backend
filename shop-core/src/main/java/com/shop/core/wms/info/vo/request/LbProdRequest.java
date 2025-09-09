package com.shop.core.wms.info.vo.request;

import com.shop.core.entity.LbVersionSeller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 적치정보 요청 클래스
 */
public class LbProdRequest {

    @Getter
    @Setter
    @Schema(name = "LbProdRequestPagingFilter", description = "라방상품정보 페이징 필터")
    public static class PagingFilter {
        @Schema(description = "라벨 파트너 ID")
        private String lbPartnerId;

        @Schema(description = "버전과 매핑된 id")
        private Integer lbVersionSellerId;

        @Schema(description = "업로드 타입 VERSION : 최초 혹은 스큐정보 수정위한, SELLER : 셀러의 판매정보를 업데이트 스큐명과 건수만 사용한다.")
        private String uploadType;

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "라방셀러id")
        private Integer lbSellerId;

        @Schema(description = "라방version 코드 80002")
        private String lbVersion;

        @Schema(description = "미판매분까지 보여준다.")
        private String status;
    }

    @Getter
    @Setter
    @Schema(name = "LbRequestExcelUpload", description = "엑셀 업로드 요청")
    public static class LiveExcelUpload {
        @Schema(description = "라방version 코드 80002")
        private String lbVersion;

        @Schema(description = "업로드 타입 VERSION : 최초 혹은 스큐정보 수정위한, SELLER : 셀러의 판매정보를 업데이트 스큐명과 건수만 사용한다.")
        private String uploadType;

        @Schema(description = "라방 버전별 셀러id")
        private Integer lbVersionSellerId;

        @Schema(description = "파일명")
        private String fileName;

        @Schema(description = "엑셀 JSON 데이타")
        private String transJson;

        

    }

    @Getter
    @Setter
    @Schema(name = "LiveExcelDownload", description = "엑셀 다운로드 요청")
    public static class LiveExcelDownload {
        @Schema(description = "라방version 코드 80002")
        private String lbVersion;

        @Schema(description = "라방셀러 map id")
        private Integer lbVersionSellerId;

        @Schema(description = "영문 색상명인지")
        private String eng;
    }


    @Getter
    @Setter
    @Schema(name = "LbRequestLiveSellerEtcUpdate", description = "라방셀러비고정보 업데이트")
    public static class LiveSellerEtcUpdate {
        @Schema(description = "라방셀러id")
        private Integer lbSellingId;

        @Schema(description = "기타정보")
        private String sellingEtc;
    }


    @Getter
    @Setter
    @Schema(name = "LbRequestLiveVersionSellerUpdate", description = "라방셀러버전 매핑 업데이트")
    public static class LbRequestLiveVersionSellerUpdate extends LbVersionSeller {
        @Schema(description = "매핑 id")
        private Integer lbVersionSellerId;
    }


    @Getter
    @Setter
    @Schema(name = "LbRequestLiveVersionSellerCopy", description = "라방셀러버전 매핑 카피(리오더)")
    public static class LbRequestLiveVersionSellerCopy {
        @Schema(description = "매핑 id")
        private Integer lbVersionSellerId;
    }
}