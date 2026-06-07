package com.shop.core.frontWeb.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.frontWeb.vo.request.DisplayRequest;
import com.shop.core.frontWeb.vo.response.DisplayResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description: Display Dao
 * Date: 2026/04/24
 * Author: park junsung
 * </pre>
 */
@Mapper
@Repository
@RequiredArgsConstructor
public class DisplayDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.Display.";

    /**
     * frontWeb 메인 페이지 영역 상품정보 목록 조회
     * @param pageRequest
     * @return ProductInfo List
     */
    public PageResponse<DisplayResponse.ProductInfoForEnum> selectProductInfoListForEnumPaging(PageRequest<DisplayRequest.ProductInfoListFilter> pageRequest) {
        List<DisplayResponse.ProductInfoForEnum> prodInfoListForEnum = sqlSession.selectList(NAMESPACE + "selectProductInfoListForEnumPaging", pageRequest);
        if (prodInfoListForEnum != null && !prodInfoListForEnum.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), prodInfoListForEnum, prodInfoListForEnum.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

}
