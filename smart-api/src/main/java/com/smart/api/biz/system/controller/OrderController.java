package com.smart.api.biz.system.controller;

import com.smart.api.annotation.AccessLog;
import com.smart.api.annotation.JwtUser;
import com.smart.api.biz.system.service.AttributeService;
import com.smart.api.biz.system.service.OrderService;
import com.smart.core.biz.common.vo.request.PageRequest;
import com.smart.core.biz.common.vo.response.PageResponse;
import com.smart.core.biz.system.vo.request.AttributeRequest;
import com.smart.core.biz.system.vo.request.MenuRequest;
import com.smart.core.biz.system.vo.request.OrderRequest;
import com.smart.core.biz.system.vo.response.ApiResponse;
import com.smart.core.biz.system.vo.response.AttributeResponse;
import com.smart.core.biz.system.vo.response.OrderResponse;
import com.smart.core.entity.User;
import com.smart.core.enums.ApiResultCode;
import com.smart.core.enums.EsseType;
import com.smart.core.utils.CommUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Description : 주문 정보 접근 Controller
 * Date :
 * Company : "Home
 * Author : park
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Tag(name = "OrderController", description = "주문조회 관련 API")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 주문_목록_조회 (페이징)
     *
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("주문관리 목록 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "주문관리 목록 조회 (페이징)")
    public ApiResponse<PageResponse<OrderResponse.Paging>> selectOrderListPaging(
            @Parameter(name = "AttributeRequestPagingFilter", description = "주문 목록 조회 (페이징) 필터", in = ParameterIn.PATH) OrderRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "주문 목록 조회 페이징") PageRequest<OrderRequest.PagingFilter> pageRequest
    ) {
        pageRequest.setFilter(filter);

        PageResponse<OrderResponse.Paging> response = orderService.selectOrderPaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }
}
