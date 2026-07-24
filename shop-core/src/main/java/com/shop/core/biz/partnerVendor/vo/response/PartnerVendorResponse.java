package com.shop.core.biz.partnerVendor.vo.response;

import com.shop.core.entity.PartnerVendor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    @Schema(description = "협력업체 페이징 응답")
    public static class Paging extends PartnerVendorResponse {

        public static Paging fromEntity(PartnerVendor vendor) {
            Paging response = new Paging();
            copyFields(vendor, response);
            return response;
        }
    }

    protected static void copyFields(PartnerVendor src, PartnerVendorResponse dst) {
        dst.setId(src.getId());
        dst.setPartnerId(src.getPartnerId());
        dst.setPartnerNm(src.getPartnerNm());
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
