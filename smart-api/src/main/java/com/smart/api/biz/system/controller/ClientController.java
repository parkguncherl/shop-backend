package com.smart.api.biz.system.controller;

import com.smart.api.annotation.AccessLog;
import com.smart.api.biz.system.service.ClientService;
import com.smart.core.biz.common.vo.request.PageRequest;
import com.smart.core.biz.common.vo.response.PageResponse;
import com.smart.core.biz.system.vo.request.ClientRequest;
import com.smart.core.biz.system.vo.response.ApiResponse;
import com.smart.core.biz.system.vo.response.ClientResponse;
import com.smart.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Description : 거래처 정보 접근 Controller
 * Date :
 * Company : "Home
 * Author : park
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/client")
@Tag(name = "ClientController", description = "거래처 조회 관련 API")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * 주문_목록_조회 (페이징)
     *
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("거래처 목록 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "거래처 목록 조회 (페이징)")
    public ApiResponse<PageResponse<ClientResponse.Paging>> selectOrderListPaging(
            @Parameter(name = "ClientRequestPagingFilter", description = "거래처 목록 조회 (페이징) 필터", in = ParameterIn.PATH) ClientRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "거래처 목록 조회 페이징") PageRequest<ClientRequest.PagingFilter> pageRequest
    ) {
        pageRequest.setFilter(filter);

        PageResponse<ClientResponse.Paging> response = clientService.selectClientPaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }
}
