package com.smart.api.biz.system.controller;

import com.smart.api.annotation.AccessLog;
import com.smart.api.biz.system.service.ContactService;
import com.smart.core.biz.common.vo.request.PageRequest;
import com.smart.core.biz.common.vo.response.PageResponse;
import com.smart.core.biz.system.vo.request.ContactRequest;
import com.smart.core.biz.system.vo.response.ApiResponse;
import com.smart.core.biz.system.vo.response.ContactResponse;
import com.smart.core.entity.Contact;
import com.smart.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * Description : 접속로그 Controller
 * Date : 2023/02/06 11:57 AM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/contact")
@Tag(name = "ContactController", description = "접속로그 관련 API")
public class ContactController {

    @Autowired
    private ContactService contactService;

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
