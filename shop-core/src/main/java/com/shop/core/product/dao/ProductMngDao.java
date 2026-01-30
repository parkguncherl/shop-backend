package com.shop.core.product.dao;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
@RequiredArgsConstructor
public class ProductMngDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.binblur.mapper.common.summary.";

    /**
     * 요약 관련 데이터 조회
     * @param forToday
     * @return 하단 요약에 필요한 데이터
     */
//    public SummaryResponse.ForToday selectSummaryForToday(SummaryRequest.ForToday forToday) {
//        return sqlSession.selectOne(NAMESPACE + "selectSummaryForToday", forToday);
//    }
}
