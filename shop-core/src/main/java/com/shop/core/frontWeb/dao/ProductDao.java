package com.shop.core.frontWeb.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.frontWeb.vo.request.ProductRequest;
import com.shop.core.frontWeb.vo.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.product.";

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
}
