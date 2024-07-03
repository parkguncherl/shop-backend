package com.binblur.api.biz.system.service;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.dao.CompanyDao;
import com.binblur.core.biz.system.vo.request.CompanyRequest;
import com.binblur.core.biz.system.vo.response.CompanyResponse;
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
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;

    /**
     * 회사_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public PageResponse<CompanyResponse.Paging> selectCompanyPaging(PageRequest<CompanyRequest.PagingFilter> pageRequest) {
        return companyDao.selectCompanyPaging(pageRequest);
    }

    /**
     * 회사_수정
     *
     * @param updateCompanyRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer updateCompany(CompanyRequest.Update updateCompanyRequest) {
        return companyDao.updateCompany(updateCompanyRequest);
    }

    /**
     * 회사_등록
     *
     * @param updateCompanyRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer insertCompany(CompanyRequest.Insert updateCompanyRequest) {
        return companyDao.insertCompany(updateCompanyRequest);
    }

    /**
     * 회사_삭제
     *
     * @param deleteCompanyRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer deleteCompany(CompanyRequest.Delete deleteCompanyRequest) {
        return companyDao.deleteCompany(deleteCompanyRequest);
    }
}
