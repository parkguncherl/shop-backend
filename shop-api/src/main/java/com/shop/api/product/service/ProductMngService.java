package com.shop.api.product.service;

import com.shop.api.biz.system.service.UserService;
import com.shop.core.entity.User;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.core.product.dao.ProductMngDao;
import com.shop.core.product.vo.request.ProductMngRequest;
import com.shop.core.product.vo.response.ProductMngResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserService userService;

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

    /**
     * 상품관리-상품정보 및 상품상세정보 추가(혹은 product 식별자(id) 가 주어질 시 상품상세정보 추가)
     * @param insertProductInfo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void InsertProductInfo(ProductMngRequest.InsertProductInfo insertProductInfo, User jwtUser) {
        if (insertProductInfo.getProductDet() == null) {
            // 상품상세정보는 필수값
            throw new CustomRuntimeException("상품상세정보를 찾을 수 없음");
        }

        /** id 존재 여부에 따라 상품정보 및 상세정보 추가 혹은 상세정보 추가로 분기 */
        if (insertProductInfo.getId() == null) {
            Integer partnerId = userService.selectPartnerIdByLoginId(jwtUser.getLoginId());

            insertProductInfo.setPartnerId(partnerId);
            Integer insertedProductCnt = productMngDao.insertProduct(insertProductInfo);

            if (insertedProductCnt != 1) {
                throw new CustomRuntimeException("상품정보를 정상적으로 추가하지 못함");
            }
        }

        Integer insertedProductDetCnt = productMngDao.insertProductDet(insertProductInfo.getProductDet());

        if (insertedProductDetCnt != 1) {
            throw new CustomRuntimeException("상품상세정보를 정상적으로 추가하지 못함");
        }
    }
}
