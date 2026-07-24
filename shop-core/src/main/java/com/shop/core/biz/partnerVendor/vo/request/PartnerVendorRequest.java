package com.shop.core.biz.partnerVendor.vo.request;

import com.shop.core.entity.PartnerVendor;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class PartnerVendorRequest {

    @Getter
    @Setter
    @Schema(description = "협력업체 검색 요청")
    public static class PagingFilter extends PartnerVendor implements RequestFilter {

        @Schema(description = "명칭 검색어")
        private String searchPartnerNm;
    }

    @Getter
    @Setter
    @Schema(name = "PartnerVendorRequestCreate", description = "협력업체 생성 요청")
    public static class Create extends PartnerVendor {
    }

    @Getter
    @Setter
    @Schema(name = "PartnerVendorRequestUpdate", description = "협력업체 수정 요청")
    public static class Update {
        @Schema(description = "협력업체 ID")
        private Integer id;

        @Schema(description = "명칭")
        private String partnerNm;

        @Schema(description = "위치")
        private String location;

        @Schema(description = "연락처")
        private String phoneNo;

        @Schema(description = "연락처2")
        private String phoneNo2;

        @Schema(description = "카톡ID")
        private String kakaoId;

        @Schema(description = "기타정보")
        private String etcInfo;

        @Schema(description = "수정자")
        private String updUser;
    }

    @Getter
    @Setter
    @Schema(description = "협력업체 삭제 요청")
    public static class Delete {
        @Schema(description = "협력업체 ID")
        private Integer id;

        @Schema(description = "수정자")
        private String updUser;
    }
}
