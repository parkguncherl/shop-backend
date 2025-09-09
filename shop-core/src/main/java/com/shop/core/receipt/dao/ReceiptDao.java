package com.shop.core.receipt.dao;

import com.shop.core.entity.PartnerPrint;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 영수증 관련 데이터 접근 객체 (DAO)
 */
@Mapper
@Repository
@RequiredArgsConstructor
public class ReceiptDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.binblur.mapper.receipt.";

    /**
     * 파트너 ID로 영수증 설정을 조회합니다.
     * @param partnerId 파트너 ID
     * @return 영수증 설정 정보
     */
    public PartnerPrint selectReceiptByPartnerId(@Param("partnerId") Integer partnerId) {
        return sqlSession.selectOne(NAMESPACE + "selectReceiptByPartnerId", partnerId);
    }

    /**
     * 새로운 영수증 설정을 생성합니다.
     * @param partnerPrint 생성할 영수증 설정 정보
     * @return 생성된 영수증 설정의 ID
     */
    public Integer insertReceipt(PartnerPrint partnerPrint) {
        sqlSession.insert(NAMESPACE + "insertReceipt", partnerPrint);
        return partnerPrint.getId();
    }

    /**
     * 기존 영수증 설정을 업데이트합니다.
     * @param partnerPrint 업데이트할 영수증 설정 정보
     * @return 업데이트된 행의 수
     */
    public int updateReceipt(PartnerPrint partnerPrint) {
        return sqlSession.update(NAMESPACE + "updateReceipt", partnerPrint);
    }
}