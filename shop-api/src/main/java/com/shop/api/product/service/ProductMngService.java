package com.shop.api.product.service;

import com.shop.core.entity.User;
import com.shop.core.product.dao.ProductMngDao;
import com.shop.core.product.vo.request.ProductMngRequest;
import com.shop.core.product.vo.response.ProductMngResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Description: ProductMng Service
 * Date: 2026/01/30 14:23
 * Author: park junsung
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductMngService {

    private final ProductMngDao productMngDao;

    /**
     * 상품관리-상품정보 조회
     * @param productInfoFilter 필터 정보를 포함한 요청 객체
     * @return 페이징된 기본정보 목록 응답
     */
    public List<ProductMngResponse.ProductInfo> selectProdInfo(ProductMngRequest.ProductInfoFilter productInfoFilter, User jwtUser) {
        // todo shopId 할당 영역 추후 작성
        return productMngDao.selectProdInfo(productInfoFilter);
    }
}
