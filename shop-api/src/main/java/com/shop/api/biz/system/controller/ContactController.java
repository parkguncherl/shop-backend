package com.shop.api.biz.system.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.system.service.ContactService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.ContactRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.ContactResponse;
import com.shop.core.entity.Contact;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Description: 접속로그 Controller
 * Date: 2023/02/06 11:57 AM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
@Tag(name = "ContactController", description = "접속로그 관련 API")
public class ContactController {

    private final ContactService contactService;

    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * 접속로그_목록_조회 (페이징)
     * @param pageRequest
     * @param filter
     * @return
     */
    @AccessLog("접속로그 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "접속로그 목록 조회 (페이징)")
    public ApiResponse<PageResponse<ContactResponse.Paging>> selectContactPaging(PageRequest<ContactRequest.PagingFilter> pageRequest, ContactRequest.PagingFilter filter) {
        log.debug("<======= ContactRequest.PagingFilter: {}", filter);

        pageRequest.setFilter(filter);
        PageResponse<ContactResponse.Paging> response = contactService.selectContactListPaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 접속로그_목록_조회 (페이징) - JWT 인증 방식
     * @param pageRequest
     * @param filter
     * @param jwtUser - JWT로 인증된 사용자 정보
     * @return
     */
    @AccessLog("접속로그 조회 JWT")
    @GetMapping(value = "/jwt/paging")
    @Operation(summary = "접속로그 JWT 목록 조회 (페이징)")
    public ApiResponse<PageResponse<ContactResponse.Paging>> selectContactListPagingjwt(
            @Parameter(name = "ContactRequestPagingFilter", description = "접속로그 조회 (페이징) 필터") ContactRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "접속로그 조회 페이징") PageRequest<ContactRequest.PagingFilter> pageRequest,
            @Parameter(hidden = true) @JwtUser User jwtUser // JWT로 인증된 사용자 정보 주입
    ) {
        log.debug("<======= ContactRequest.PagingFilter: {}", filter);

        // JWT 인증된 사용자를 이용한 추가적인 로직이 필요할 수 있습니다
        pageRequest.setFilter(filter);  // 필터를 pageRequest에 설정
        PageResponse<ContactResponse.Paging> response = contactService.selectContactListPagingjwt(pageRequest, jwtUser); // 서비스에서 페이징된 접속로그 목록 조회

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);  // 성공적으로 응답 반환
    }


    /**
     * 접속로그_조회 (by Id)
     * @param contactId
     * @return
     */
    @GetMapping(value = "/{contactId}")
    @Operation(summary = "접속로그 조회")
    public ApiResponse<Contact> selectContactById(@PathVariable Integer contactId) {

        log.debug(">>>>>> contactId = {}", contactId);
        Contact contact = contactService.selectContactById(contactId);
        log.debug(">>>>>> contact = {}", contact);

        if (contact == null) {
            return new ApiResponse<>(ApiResultCode.DATA_NOT_FOUND);
        }

        return new ApiResponse<>(contact);
    }

}
