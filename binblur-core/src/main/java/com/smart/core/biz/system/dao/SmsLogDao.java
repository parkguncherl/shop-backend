package com.binblur.core.biz.system.dao;

import com.binblur.core.entity.Sms;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description : smslog Dao
 * Date : 2023/02/14 11:58 AM
 * Company : smart90
 * Author : luckeey
 * </pre>
 */
@Repository
public class SmsLogDao {

    private final String PRE_NS = "com.binblur.mapper.smsLog.";
    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;

    /**
     * sms전송_리스트_조회
     * @return
     */
    public List<Sms> selectSmsList() {
        return sqlSession.selectList(PRE_NS.concat("selectSmsList"));
    }


    /**
     * 문자 메시지 등록
     *
     * @param sms
     * @return
     */
    public Integer updateSmsLog(Sms sms) {
        return sqlSession.update(PRE_NS.concat("updateSmsLog"), sms);
    }


}
