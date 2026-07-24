package com.shop.api.biz.partnerVendor.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.partnerVendor.dao.PartnerVendorDao;
import com.shop.core.biz.partnerVendor.vo.request.PartnerVendorRequest;
import com.shop.core.biz.partnerVendor.vo.response.PartnerVendorResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.PartnerVendor;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partnerVendorMng")
@Tag(name = "PartnerVendorMngController", description = "협력업체 관리 API")
@RequiredArgsConstructor
public class PartnerVendorMngController {

    private final PartnerVendorDao partnerVendorDao;

    @AccessLog("협력업체 목록 조회")
    @GetMapping("/list")
    @Operation(summary = "협력업체 목록 조회 (페이징)")
    public ApiResponse<PageResponse<PartnerVendorResponse.Paging>> getList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            PageRequest<PartnerVendorRequest.PagingFilter> pageRequest
    ) {
        if (pageRequest.getFilter() == null) pageRequest.setFilter(new PartnerVendorRequest.PagingFilter());
        pageRequest.getFilter().setPartnerId(jwtUser.getPartnerId());
        return new ApiResponse<>(ApiResultCode.SUCCESS, partnerVendorDao.selectPartnerVendorListPaging(pageRequest));
    }

    @AccessLog("협력업체 콤보 목록 조회")
    @GetMapping("/dropdown")
    @Operation(summary = "협력업체 콤보(드롭다운) 목록 조회")
    public ApiResponse<java.util.List<PartnerVendor>> getDropdown(
            @Parameter(hidden = true) @JwtUser User jwtUser
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, partnerVendorDao.selectPartnerVendorDropdown(jwtUser.getPartnerId()));
    }

    @AccessLog("협력업체 단건 조회")
    @GetMapping("/{id}")
    @Operation(summary = "협력업체 단건 조회")
    public ApiResponse<PartnerVendor> getOne(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer id
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, partnerVendorDao.selectPartnerVendorById(id));
    }

    @AccessLog("협력업체 등록")
    @PostMapping("/create")
    @Operation(summary = "협력업체 등록")
    public ApiResponse<Void> create(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody PartnerVendorRequest.Create request
    ) {
        request.setPartnerId(jwtUser.getPartnerId());
        request.setCreUser(jwtUser.getLoginId());
        request.setUpdUser(jwtUser.getLoginId());
        partnerVendorDao.insertPartnerVendor(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    @AccessLog("협력업체 수정")
    @PutMapping("/update")
    @Operation(summary = "협력업체 수정")
    public ApiResponse<Void> update(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody PartnerVendorRequest.Update request
    ) {
        request.setUpdUser(jwtUser.getLoginId());
        partnerVendorDao.updatePartnerVendorExistOnly(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }

    @AccessLog("협력업체 삭제")
    @DeleteMapping("/{id}")
    @Operation(summary = "협력업체 삭제")
    public ApiResponse<Void> delete(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer id
    ) {
        PartnerVendorRequest.Delete request = new PartnerVendorRequest.Delete();
        request.setId(id);
        request.setUpdUser(jwtUser.getLoginId());
        partnerVendorDao.deletePartnerVendor(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS, null);
    }
}
