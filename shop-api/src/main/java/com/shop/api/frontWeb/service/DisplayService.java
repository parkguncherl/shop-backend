package com.shop.api.frontWeb.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.GuestToken;
import com.shop.core.frontWeb.dao.DisplayDao;
import com.shop.core.frontWeb.vo.request.DisplayRequest;
import com.shop.core.frontWeb.vo.response.DisplayResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Description: DisplayService
 * Date: 2026/03/09
 * Author: park gun cheol
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DisplayService {

    private final DisplayDao displayDao;


    /**
     * frontWeb 메인 페이지 영역 상품정보 목록 조회 비즈니스 레이어
     * @param pageRequest
     * @return DisplayResponse.ProductInfoForEnum List
     */
    public PageResponse<DisplayResponse.ProductInfoForEnum> selectProductInfoListForEnumPaging(PageRequest<DisplayRequest.ProductInfoListFilter> pageRequest) {
        return displayDao.selectProductInfoListForEnumPaging(pageRequest);
    }

}
