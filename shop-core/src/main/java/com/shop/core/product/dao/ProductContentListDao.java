package com.shop.core.product.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.product.vo.request.ProductContentListRequest;
import com.shop.core.product.vo.response.ProductContentListResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description: ProductContentList Dao
 * Date: 2026/02/13
 * Author: park junsung
 * </pre>
 */
@Mapper
@Repository
@RequiredArgsConstructor
public class ProductContentListDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.shop.mapper.product.productContentList.";

    /**
     * 상품관리-상품컨텐츠목록 조회
     * @param pageRequest
     * @return 페이징된 ProductContent List
     */
    public PageResponse<ProductContentListResponse.ProductContent> selectProdContentList(PageRequest<ProductContentListRequest.ProductContentListFilter> pageRequest) {
        List<ProductContentListResponse.ProductContent> prodContentList = sqlSession.selectList(NAMESPACE + "selectProdContentList", pageRequest);
        if (prodContentList != null && !prodContentList.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), prodContentList, prodContentList.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }
}
