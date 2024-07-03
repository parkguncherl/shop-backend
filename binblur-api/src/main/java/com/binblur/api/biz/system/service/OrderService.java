package com.binblur.api.biz.system.service;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.dao.AttributeDao;
import com.binblur.core.biz.system.dao.OrderDao;
import com.binblur.core.biz.system.vo.request.AttributeRequest;
import com.binblur.core.biz.system.vo.request.MenuRequest;
import com.binblur.core.biz.system.vo.request.OrderRequest;
import com.binblur.core.biz.system.vo.request.UserRequest;
import com.binblur.core.biz.system.vo.response.AttributeResponse;
import com.binblur.core.biz.system.vo.response.OrderResponse;
import com.binblur.core.biz.system.vo.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Description : 속성_관리 Service
 * Company : home
 * Author : park
 * </pre>
 */
@Slf4j
@Service
@Transactional(transactionManager = "dataTxManager")
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     * 주문_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public PageResponse<OrderResponse.Paging> selectOrderPaging(PageRequest<OrderRequest.PagingFilter> pageRequest) {
        return orderDao.selectOrderPaging(pageRequest);
    }
}
