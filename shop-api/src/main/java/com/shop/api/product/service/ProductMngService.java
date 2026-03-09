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

import java.util.List;


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
     * 상품관리-상품컨텐츠목록 조회
     * @param productInfoFilter
     * @return ProductInfo List
     */
    public List<ProductMngResponse.ProductInfo> selectProdInfoList(ProductMngRequest.ProductInfoFilter productInfoFilter, User jwtUser) {
        return productMngDao.selectProdInfoList(productInfoFilter);
    }

    /**
     * 상품관리-상품정보 상세 조회
     * @param productDetInfoFilter
     * @return ProductDetInfo List
     */
    public List<ProductMngResponse.ProductDetInfo> selectProdDetInfo(ProductMngRequest.ProductDetInfoFilter productDetInfoFilter, User jwtUser) {
        return productMngDao.selectProdDetInfo(productDetInfoFilter);
    }
}
