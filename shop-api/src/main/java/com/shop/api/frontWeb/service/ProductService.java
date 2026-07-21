package com.shop.api.frontWeb.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.GuestToken;
import com.shop.core.frontWeb.dao.ProductDao;
import com.shop.core.frontWeb.vo.request.ProductRequest;
import com.shop.core.frontWeb.vo.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * frontWeb 이하 상품 목록 조회 (categoryId 필수 버전) 비즈니스 레이어
     * @param pageRequest categoryId 가 반드시 포함된다는 가정
     * @return ProductInfo PageResponse
     */
    public PageResponse<ProductResponse.ProductInfo> selectProductInfoListByCategory(PageRequest<ProductRequest.ProductInfoListFilter> pageRequest) {
        return productDao.selectProductInfoListByCategory(pageRequest);
    }

    /**
     * 상품 검색 비즈니스 레이어 (상품명 + 색상)
     */
    public PageResponse<ProductResponse.ProductInfo> selectProductSearchList(PageRequest<ProductRequest.ProductSearchFilter> pageRequest) {
        return productDao.selectProductSearchList(pageRequest);
    }

    /**
     * 메인페이지 상품 목록
     */
    public List<ProductResponse.ProductInfo> selectProductListForMain(GuestToken guestUser) {
        return productDao.selectProductListForMain(guestUser.getPartnerId()); // 시즌을 제외한
    }


    /**
     * 상품 상세 조회 비즈니스 레이어
     * @param param productId, partnerId
     * @return ProductDetail (상품정보 + SKU목록 + 연관상품목록)
     */
    public ProductResponse.ProductDetail selectProductDetail(ProductRequest.ProductDetailParam param) {
        ProductResponse.ProductDetail detail = productDao.selectProductDetail(param);
        if (detail == null) return null;

        detail.setDetList(productDao.selectProductDetList(param));

        List<ProductResponse.RelatedProductInfo> relList = productDao.selectRelatedProductList(param);
        detail.setRelatedList(relList.stream()
                .filter(v -> !v.getId().equals(param.getProductId())) // 본인정보는 제외
                .toList());
        return detail;
    }
}
