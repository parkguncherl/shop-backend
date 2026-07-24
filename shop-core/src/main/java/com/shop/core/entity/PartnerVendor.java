package com.shop.core.entity;

import com.shop.core.interfaces.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * Description: 협력업체 Entity (TB_PARTNER_VENDOR)
 * Date: 2026/07/24
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "협력업체 Entity")
public class PartnerVendor extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4515012303177206570L;

    @Schema(description = "아이디(PK)")
    private Integer id;

    @Schema(description = "매장(파트너) ID")
    private Integer partnerId;

    @Schema(description = "명칭")
    private String vendorNm;

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
}
