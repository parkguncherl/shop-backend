package com.shop.core.biz.system.vo.response;

import com.shop.core.entity.Attribute;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class AttributeResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "AttributeResponsePaging", description = "속성 페이징 응답", type = "object")
    public static class Paging extends Attribute {

    }
}
