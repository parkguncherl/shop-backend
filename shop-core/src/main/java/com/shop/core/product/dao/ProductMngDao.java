package com.shop.core.product.dao;

import com.shop.core.product.vo.request.ProductMngRequest;
import com.shop.core.product.vo.response.ProductMngResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@RequiredArgsConstructor
public class ProductMngDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.shop.mapper.product.productMng.";

    /**
     * 상품관리-상품정보 조회
     * @param productInfoFilter
     * @return ProductInfo List
     */
    public List<ProductMngResponse.ProductInfo> selectProdInfo(ProductMngRequest.ProductInfoFilter productInfoFilter) {
        return sqlSession.selectList(NAMESPACE + "selectProdInfo", productInfoFilter);
    }

    /**
     * 상품관리-상품정보 상세 조회
     * @param productDetInfoFilter
     * @return ProductDetInfo List
     */
    public List<ProductMngResponse.ProductDetInfo> selectProdDetInfo(ProductMngRequest.ProductDetInfoFilter productDetInfoFilter) {
        return sqlSession.selectList(NAMESPACE + "selectProdDetInfo", productDetInfoFilter);
    }
}
