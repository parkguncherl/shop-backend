package com.binblur.core.biz.common.dao;

import com.binblur.core.entity.BatchLog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * Description : 배치로그 Dao
 * Date : 2023/04/03 11:58 AM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
@Repository
public class BatchLogDao {

    private final String PRE_NS = "com.binblur.mapper.batchLog.";

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;


    /**
     * 배치로그_등록
     * @param batchLog
     * @return
     */
    public Integer insertBatchLog(BatchLog batchLog) {
        return sqlSession.insert(PRE_NS.concat("insertBatchLog"), batchLog);
    }
}
