package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class DeliveryAddressResponse {

    @Getter
    @Setter
    @Schema(name = "DeliveryAddressResponseInfo", description = "배송지 응답")
    public static class Info {
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
        private LocalDateTime creTm;
        private LocalDateTime uptTm;
    }
}
