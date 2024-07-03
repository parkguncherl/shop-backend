package com.binblur.api.biz.system.service;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.dao.AttributeDao;
import com.binblur.core.biz.system.vo.request.AttributeRequest;
import com.binblur.core.biz.system.vo.request.MenuRequest;
import com.binblur.core.biz.system.vo.request.UserRequest;
import com.binblur.core.biz.system.vo.response.AttributeResponse;
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
public class AttributeService {

    @Autowired
    private AttributeDao attributeDao;

    /**
     * 속성_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public PageResponse<AttributeResponse.Paging> selectAttributePaging(PageRequest<AttributeRequest.PagingFilter> pageRequest) {
        return attributeDao.selectAttributePaging(pageRequest);
    }

    /**
     * 속성_수정_추가
     *
     * @param modifyAttributeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer insertAttribute(AttributeRequest.Modified modifyAttributeRequest) {
        if (modifyAttributeRequest.getInserted().isEmpty()) return 0;
        return attributeDao.insertAttribute(modifyAttributeRequest.getInserted());
    }

    /**
     * 속성_수정_갱신
     *
     * @param modifyAttributeRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer updateAttribute(AttributeRequest.Modified modifyAttributeRequest) {
        if (modifyAttributeRequest.getUpdated().isEmpty()) return 0;
        return attributeDao.updateAttribute(modifyAttributeRequest.getUpdated());
    }
}
