package com.shop.core.biz.platform.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.platform.vo.request.PlatformProductRequest;
import com.shop.core.frontWeb.vo.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description: 플랫폼(DDM CHOICE) 상품 조회 Dao
 * </pre>
 */
@Mapper
@Repository
@RequiredArgsConstructor
public class PlatformProductDao {

    private final SqlSession sqlSession;

    private static final String NAMESPACE = "com.shop.mapper.biz.platform.PlatformProduct.";

    /**
     * 플랫폼 상품 목록 조회 (카테고리: 대분류 LIKE / 소분류 EQUAL)
     *
     * @param pageRequest majorCode(LIKE) 또는 minorCode(=) 필터
     * @return ProductInfo PageResponse
     */
    public PageResponse<ProductResponse.ProductInfo> selectProductInfoListPaging(PageRequest<PlatformProductRequest.ProductListFilter> pageRequest) {
        List<ProductResponse.ProductInfo> list = sqlSession.selectList(NAMESPACE + "selectProductInfoListPaging", pageRequest);
        if (list != null && !list.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), list, list.size());
        }
        return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
    }
}
