package com.binblur.core.biz.system.vo.response;

import com.binblur.core.entity.Order;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class OrderResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "OrderResponsePaging", description = "주문 페이징 응답", type = "object")
    public static class Paging extends Order {
        @Schema(description = "no")
        @Parameter(description = "no")
        private String no;
    }
}
