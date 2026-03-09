package com.shop.api.product.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.User;
import com.shop.core.product.dao.ProductMngDao;
import com.shop.core.product.vo.request.ProductMngRequest;
import com.shop.core.product.vo.response.ProductMngResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * <pre>
 * Description: ProductMng Service
 * Date: 2026/03/09
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
    /**
     * 상품관리-상품컨텐츠목록 조회
     * @param pageRequest
     * @return 페이징된 ProductInfo List
     */
    public PageResponse<ProductMngResponse.ProductInfo> selectProdInfoList(PageRequest<ProductMngRequest.ProductInfoFilter> pageRequest, User jwtUser) {
//        pageRequest.getFilter().setPartnerId(userService.selectPartnerIdByLoginId(jwtUser.getLoginId()));
//        pageRequest.getFilter().setNewsType(GlobalConst.PRODUCT_CONTENTS_NEWS_TYPE.getCode());
        return productMngDao.selectProdInfoList(pageRequest);
    }
}
