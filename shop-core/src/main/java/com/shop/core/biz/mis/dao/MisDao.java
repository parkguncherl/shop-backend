package com.shop.core.biz.mis.dao;

import com.shop.core.biz.mis.vo.response.MisResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
@RequiredArgsConstructor
public class MisDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.biz.Mis.";

    public List<MisResponse.ProductViewItem> selectProductViewList(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("fromDateTime", fromDateTime);
        params.put("toDateTime", toDateTime);
        return sqlSession.selectList(NAMESPACE + "selectProductViewList", params);
    }
}
