package com.shop.api.frontWeb.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.frontWeb.dao.ProductDao;
import com.shop.core.frontWeb.vo.request.ProductRequest;
import com.shop.core.frontWeb.vo.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Description: ProductService
 * Date: 2026/05/13
 * Author: park junsung
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    /**
     * frontWeb 이하 상품 목록 조회(when code etc of partner_code is 'PROD') 비즈니스 레이어
     * @param pageRequest
     * @return ProductInfo PageResponse
     */
    public PageResponse<ProductResponse.ProductInfo> selectProductInfoListPaging(PageRequest<ProductRequest.ProductInfoListFilter> pageRequest) {
        return productDao.selectProductInfoListPaging(pageRequest);
    }
}
