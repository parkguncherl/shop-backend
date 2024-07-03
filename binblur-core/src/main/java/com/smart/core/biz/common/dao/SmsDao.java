package com.binblur.core.biz.common.dao;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.request.SmsRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.common.vo.response.SmsResponse;
import com.binblur.core.entity.Sms;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description : 메뉴_관리 Dao
 * Date : 2023/02/14 11:58 AM
 * Company : smart90
 * Author : luckeey
 * </pre>
 */
@Repository
public class SmsDao {

    private final String PRE_NS = "com.binblur.mapper.sms.";

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;


    /**
     * 문자 메시지 등록
     *
     * @param sms
     * @return
     */
    public Integer insertSmsLog(Sms sms) {
        return sqlSession.insert(PRE_NS.concat("insertSmsLog"), sms);
    }

    /**
     * 서버이벤트 POST ID별 SMS 페이징 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<SmsResponse.SvrEventPaging> selectSmsListPagingForSvrEvent(PageRequest<SmsRequest.SvrEventPagingFilter> pageRequest) {
        List<SmsResponse.SvrEventPaging> listPage = sqlSession.selectList(PRE_NS.concat("selectSmsListPagingForSvrEvent"), pageRequest);
        if (listPage != null && !listPage.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), listPage, listPage.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 충전기_이벤트_SMS_이력_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<SmsResponse.PostEventPaging> selectEventSmsHistoryPaging(PageRequest<SmsRequest.PostEventSmsHistoryPagingFilter> pageRequest) {
        List<SmsResponse.PostEventPaging> smsHistorys = sqlSession.selectList(PRE_NS.concat("selectEventSmsHistoryPaging"), pageRequest);
        if (smsHistorys != null && !smsHistorys.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), smsHistorys, smsHistorys.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }
}
