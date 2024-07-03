package com.binblur.core.biz.system.dao;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.vo.request.OrderRequest;
import com.binblur.core.biz.system.vo.response.OrderResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description : 주문_관리 Dao
 * Company : home
 * Author : park
 * </pre>
 */
@Repository
public class OrderDao {

    private final String PRE_NS = "com.binblur.mapper.order.";

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession; // MybatisAutoConfiguration.sqlSessionTemplate

    /**
     * 주문_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */

    public PageResponse<OrderResponse.Paging> selectOrderPaging(PageRequest<OrderRequest.PagingFilter> pageRequest) {
        List<OrderResponse.Paging> OrderList = sqlSession.selectList(PRE_NS.concat("selectOrderListPaging"), pageRequest);
        if (OrderList != null && !OrderList.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), OrderList, OrderList.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }
}
