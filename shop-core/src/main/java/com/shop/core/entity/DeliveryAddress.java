package com.shop.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "DeliveryAddress", description = "FO delivery address entity")
public class DeliveryAddress {

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
    private String delYn;
    private LocalDateTime creTm;
    private LocalDateTime uptTm;
}
