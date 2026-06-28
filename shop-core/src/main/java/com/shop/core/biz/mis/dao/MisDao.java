package com.shop.core.biz.mis.dao;

import com.shop.core.biz.mis.vo.request.MisRequest;
import com.shop.core.biz.mis.vo.response.MisResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

@Mapper
@Repository
@RequiredArgsConstructor
public class MisDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.biz.Mis.";

    public List<MisResponse.ProductViewItem> selectProductViewList(MisRequest.ListFilter filter) {
        return sqlSession.selectList(NAMESPACE + "selectProductViewList", filter);
    }

    public List<MisResponse.SalesStatItem> selectSalesStatList(MisRequest.SalesStatFilter filter) {
        return sqlSession.selectList(NAMESPACE + "selectSalesStatList", filter);
    }

    public List<MisResponse.ProductViewItem> selectSalesStatDetailList(MisRequest.SalesStatDetailFilter filter) {
        return sqlSession.selectList(NAMESPACE + "selectSalesStatDetailList", filter);
    }

    public MisResponse.DailySalesStat selectDailySalesStat(Integer partnerId) {
        return sqlSession.selectOne(NAMESPACE + "selectDailySalesStat", partnerId);
    }

    public List<MisResponse.CategoryViewItem> selectCategoryViewList(MisRequest.CategoryViewFilter filter) {
        return sqlSession.selectList(NAMESPACE + "selectCategoryViewList", filter);
    }

    public List<MisResponse.ReviewFitItem> selectReviewFitAnalysis(MisRequest.ReviewFitFilter filter) {
        return sqlSession.selectList(NAMESPACE + "selectReviewFitAnalysis", filter);
    }
}
