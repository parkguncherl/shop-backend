package com.binblur.api.biz.system.service;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.dao.ClientDao;
import com.binblur.core.biz.system.vo.request.*;
import com.binblur.core.biz.system.vo.response.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Description : 거래처_관리 Service
 * Company : home
 * Author : park
 * </pre>
 */
@Slf4j
@Service
@Transactional(transactionManager = "dataTxManager")
public class ClientService {

    @Autowired
    private ClientDao clientDao;

    /**
     * 주문_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public PageResponse<ClientResponse.Paging> selectClientPaging(PageRequest<ClientRequest.PagingFilter> pageRequest) {
        return clientDao.selectClientPaging(pageRequest);
    }
}
