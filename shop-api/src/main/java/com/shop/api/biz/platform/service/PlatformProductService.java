package com.shop.api.biz.platform.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.platform.dao.PlatformProductDao;
import com.shop.core.biz.platform.vo.request.PlatformProductRequest;
import com.shop.core.frontWeb.vo.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Description: 플랫폼(DDM CHOICE) 상품 조회 Service
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlatformProductService {

    private final PlatformProductDao platformProductDao;

    /**
     * 플랫폼 상품 목록 조회 (대분류 LIKE / 소분류 EQUAL)
     */
    public PageResponse<ProductResponse.ProductInfo> selectProductInfoListPaging(PageRequest<PlatformProductRequest.ProductListFilter> pageRequest) {
        return platformProductDao.selectProductInfoListPaging(pageRequest);
    }
}
