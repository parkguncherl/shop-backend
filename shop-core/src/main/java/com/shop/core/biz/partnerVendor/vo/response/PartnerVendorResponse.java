package com.shop.core.biz.partnerVendor.vo.response;

import com.shop.core.entity.PartnerVendor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "협력업체 응답 VO")
public class PartnerVendorResponse extends PartnerVendor {

    public static PartnerVendorResponse fromEntity(PartnerVendor vendor) {
        PartnerVendorResponse response = new PartnerVendorResponse();
        copyFields(vendor, response);
        return response;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(name = "VendorMngResponseVendorPagingInfo", description = "협력업체 페이징 응답", type = "object")
    public static class Paging extends PartnerVendorResponse {

        @Schema(description = "상품 개수")
        private Integer prodCnt;

        public static Paging fromEntity(PartnerVendor vendor, Integer prodCnt) {
            Paging response = new Paging();
            copyFields(vendor, response);
            response.setProdCnt(prodCnt);
            return response;
        }
    }

    protected static void copyFields(PartnerVendor src, PartnerVendorResponse dst) {
        dst.setId(src.getId());
        dst.setPartnerId(src.getPartnerId());
        dst.setVendorNm(src.getVendorNm());
        dst.setLocation(src.getLocation());
        dst.setPhoneNo(src.getPhoneNo());
        dst.setPhoneNo2(src.getPhoneNo2());
        dst.setKakaoId(src.getKakaoId());
        dst.setEtcInfo(src.getEtcInfo());
        dst.setCreTm(src.getCreTm());
        dst.setCreUser(src.getCreUser());
        dst.setUpdTm(src.getUpdTm());
        dst.setUpdUser(src.getUpdUser());
    }
}
