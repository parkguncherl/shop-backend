package com.shop.core.biz.common.dao;

import com.shop.core.entity.PartnerPrint;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PartnerPrintDao {

    private final String PRE_NS = "com.binblur.mapper.common.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 주어진 파트너 ID에 해당하는 프린트 설정을 조회합니다.
     *
     * @param partnerId 조회할 파트너의 ID
     * @return 파트너의 프린트 설정 정보. 없으면 null 반환
     */
    public PartnerPrint selectPartnerPrintByPartnerId(Integer partnerId) {
        return sqlSession.selectOne(PRE_NS.concat("selectPartnerPrintByPartnerId"), partnerId);
    }
}