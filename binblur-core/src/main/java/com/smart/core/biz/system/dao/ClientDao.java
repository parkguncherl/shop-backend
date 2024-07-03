package com.binblur.core.biz.system.dao;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.vo.request.ClientRequest;
import com.binblur.core.biz.system.vo.response.ClientResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description : 거래처_관리 Dao
 * Company : home
 * Author : park
 * </pre>
 */
@Repository
public class ClientDao {

    private final String PRE_NS = "com.binblur.mapper.client.";

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession; // MybatisAutoConfiguration.sqlSessionTemplate

    /**
     * 주문_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */

    public PageResponse<ClientResponse.Paging> selectClientPaging(PageRequest<ClientRequest.PagingFilter> pageRequest) {
        List<ClientResponse.Paging> ClientList = sqlSession.selectList(PRE_NS.concat("selectClientListPaging"), pageRequest);
        if (ClientList != null && !ClientList.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), ClientList, ClientList.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }
}
