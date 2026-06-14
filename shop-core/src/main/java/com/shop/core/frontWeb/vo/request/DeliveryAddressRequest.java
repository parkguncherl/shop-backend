package com.shop.core.frontWeb.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class DeliveryAddressRequest {

    @Getter
    @Setter
    @Schema(name = "DeliveryAddressRequestSave", description = "배송지 저장 요청")
    public static class Save {
        private Long socialAccountId;
        private String alias;
        private String receiverName;
        private String receiverPhone;
        private String zipCode;
        private String address;
        private String addressDetail;
        private String memo;
        private String isDefault;   // Y / N
    }

    @Getter
    @Setter
    @Schema(name = "DeliveryAddressRequestUpdate", description = "배송지 수정 요청")
    public static class Update {
        private Long id;
        private Long socialAccountId;
        private String alias;
        private String receiverName;
        private String receiverPhone;
        private String zipCode;
        private String address;
        private String addressDetail;
        private String memo;
        private String isDefault;
    }
}
