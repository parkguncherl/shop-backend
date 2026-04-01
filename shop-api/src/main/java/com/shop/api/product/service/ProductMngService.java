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
     * 상품관리-카테고리 연결상품정보 목록 조회
     * @param categoryProductInfoFilter
     * @return CategoryProductInfo List
     */
    public List<ProductMngResponse.CategoryProductInfo> selectCategoryProductInfoList(ProductMngRequest.CategoryProductInfoFilter categoryProductInfoFilter, User jwtUser) {
        return productMngDao.selectCategoryProductInfoList(categoryProductInfoFilter);
    }

    /**
     * 상품관리-상품정보 및 상품상세정보 추가(혹은 product 식별자(id) 가 주어질 시 상품상세정보 추가) 관련 비즈니스 동작 처리
     * @param insertProductInfo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertProductInfo(ProductMngRequest.InsertProductInfo insertProductInfo, User jwtUser) {
        if (insertProductInfo.getProductDet() == null) {
            // 상품상세정보는 필수값
            throw new CustomRuntimeException("상품상세정보를 찾을 수 없음");
        }

        /** id 존재 여부에 따라 상품정보 및 상세정보 추가 혹은 상세정보 추가로 분기 */
        // todo 마이그레이션 이후 더 이상 무의미하다 여겨질 시 제거
//        if (insertProductInfo.getId() == null) {
//            Integer partnerId = userService.selectPartnerIdByLoginId(jwtUser.getLoginId());
//
//            insertProductInfo.setPartnerId(partnerId);
//
//            insertProductInfo.setCreUser(jwtUser.getLoginId());
//            insertProductInfo.setUpdUser(jwtUser.getLoginId());
//            Integer insertedProductCnt = productMngDao.insertProduct(insertProductInfo);
//
//            if (insertedProductCnt != 1) {
//                throw new CustomRuntimeException("상품정보를 정상적으로 추가하지 못함");
//            }
//        }

        Integer partnerId = userService.selectPartnerIdByLoginId(jwtUser.getLoginId());

        insertProductInfo.setPartnerId(partnerId);

        insertProductInfo.setCreUser(jwtUser.getLoginId());
        insertProductInfo.setUpdUser(jwtUser.getLoginId());
        Integer insertedProductCnt = productMngDao.insertProduct(insertProductInfo);

        if (insertedProductCnt != 1) {
            throw new CustomRuntimeException("상품정보를 정상적으로 추가하지 못함");
        }

        insertProductInfo.getProductDet().setProductId(insertProductInfo.getId()); // prod Id 할당(요청 시점에 전달된 값 혹은 insert 시점에 할당되어진 값)

        insertProductInfo.getProductDet().setCreUser(jwtUser.getLoginId());
        insertProductInfo.getProductDet().setUpdUser(jwtUser.getLoginId());
        Integer insertedProductDetCnt = productMngDao.insertProductDet(insertProductInfo.getProductDet());

        if (insertedProductDetCnt != 1) {
            throw new CustomRuntimeException("상품상세정보를 정상적으로 추가하지 못함");
        }
    }

    /**
     * 상품관리-상품상세정보 추가 관련 비즈니스 동작 처리
     * @param insertProductDet
     * @return
     */
    public Integer insertProductDet(ProductMngRequest.InsertProductDet insertProductDet, User jwtUser) {
        if (insertProductDet.getProductId() == null) {
            throw new IllegalArgumentException("연관 상품정보 식별자를 찾을 수 없음");
        }
        insertProductDet.setCreUser(jwtUser.getLoginId());
        insertProductDet.setUpdUser(jwtUser.getLoginId());
        return productMngDao.insertProductDet(insertProductDet);
    }

    /**
     * 상품관리-상품정보 수정 관련 비즈니스 동작 처리
     * @param updateProduct
     * @return
     */
    public Integer updateProduct(ProductMngRequest.UpdateProduct updateProduct, User jwtUser) {

        /** 계절 식별 영역 기본값 할당 영역 */
        if (updateProduct.getIsSpring() == null) {
            updateProduct.setIsSpring("N");
        }
        if (updateProduct.getIsSummer() == null) {
            updateProduct.setIsSummer("N");
        }
        if (updateProduct.getIsAutumn() == null) {
            updateProduct.setIsAutumn("N");
        }
        if (updateProduct.getIsWinter() == null) {
            updateProduct.setIsWinter("N");
        }

        updateProduct.setUpdUser(jwtUser.getLoginId());
        return productMngDao.updateProduct(updateProduct);
    }

    /**
     * 상품관리-상품상세정보 수정 관련 비즈니스 동작 처리
     * @param updateProductDet
     * @return
     */
    public Integer updateProductDet(ProductMngRequest.UpdateProductDet updateProductDet, User jwtUser) {
        updateProductDet.setUpdUser(jwtUser.getLoginId());
        return productMngDao.updateProductDet(updateProductDet);
    }

    /**
     * 상품관리-상품정보 삭제 관련 비즈니스 동작 처리
     * @param deleteProduct
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer deleteProduct(ProductMngRequest.DeleteProduct deleteProduct, User jwtUser) {
        ProductMngRequest.ProductDetInfoFilter productDetInfoFilter = new ProductMngRequest.ProductDetInfoFilter();
        productDetInfoFilter.setProdId(deleteProduct.getId());

        List<ProductMngResponse.ProductDetInfo> productDetInfoList = productMngDao.selectProdDetInfo(productDetInfoFilter);
        if (!productDetInfoList.isEmpty()) {
            throw new CustomRuntimeException("주어진 식별자에 대응하는 상품 이하 연관된 상품상세정보가 잔존하는 경우 삭제할 수 없음");
        }

        deleteProduct.setUpdUser(jwtUser.getLoginId());
        return productMngDao.deleteProduct(deleteProduct);
    }

    /**
     * 상품관리-상품상세정보 삭제 관련 비즈니스 동작 처리
     * @param deleteProductDet
     * @return
     */
    public Integer deleteProductDet(ProductMngRequest.DeleteProductDet deleteProductDet, User jwtUser) {
        deleteProductDet.setUpdUser(jwtUser.getLoginId());
        return productMngDao.deleteProductDet(deleteProductDet);
    }
}
