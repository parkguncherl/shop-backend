package com.shop.core.frontWeb.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.frontWeb.vo.request.ProductRequest;
import com.shop.core.frontWeb.vo.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Description: Product Dao
 * Date: 2026/05/13
 * Author: park junsung
 * </pre>
 */
@Mapper
@Repository
@RequiredArgsConstructor
public class ProductDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.Product.";

    /**
     * frontWeb 이하 상품 목록 조회(when code etc of partner_code is 'PROD')
     * @param pageRequest
     * @return ProductInfo PageResponse
     */
    public PageResponse<ProductResponse.ProductInfo> selectProductInfoListPaging(PageRequest<ProductRequest.ProductInfoListFilter> pageRequest) {
        List<ProductResponse.ProductInfo> productInfoList = sqlSession.selectList(NAMESPACE + "selectProductInfoListPaging", pageRequest);
        if (productInfoList != null && !productInfoList.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), productInfoList, productInfoList.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 상품 검색 (상품명 + 색상 LIKE)
     */
    public PageResponse<ProductResponse.ProductInfo> selectProductSearchList(PageRequest<ProductRequest.ProductSearchFilter> pageRequest) {
        List<ProductResponse.ProductInfo> list = sqlSession.selectList(NAMESPACE + "selectProductSearchList", pageRequest);
        if (list != null && !list.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), list, list.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 상품 상세 조회 (TB_PRODUCT + 이미지)
     */
    public ProductResponse.ProductDetail selectProductDetail(ProductRequest.ProductDetailParam param) {
        return sqlSession.selectOne(NAMESPACE + "selectProductDetail", param);
    }

    /**
     * 상품 상세(SKU) 목록 조회 (TB_PRODUCT_DET)
     */
    public List<ProductResponse.ProductDetInfo> selectProductDetList(ProductRequest.ProductDetailParam param) {
        List<ProductResponse.ProductDetInfo> list = sqlSession.selectList(NAMESPACE + "selectProductDetList", param);
        return list != null ? list : Collections.emptyList();
    }

    /**
     * 연관 상품 목록 조회 (같은 카테고리, 현재 상품 제외)
     */
    public List<ProductResponse.RelatedProductInfo> selectRelatedProductList(ProductRequest.ProductDetailParam param) {
        List<ProductResponse.RelatedProductInfo> list = sqlSession.selectList(NAMESPACE + "selectRelatedProductList", param);
        return list != null ? list : Collections.emptyList();
    }
}
