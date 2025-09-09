package com.shop.api.biz.system.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.dao.CompanyDao;
import com.shop.core.biz.system.vo.request.CompanyRequest;
import com.shop.core.biz.system.vo.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Description: 속성_관리 Service
 * Company: home
 * Author : park
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "dataTxManager")
public class CompanyService {

    private final CompanyDao companyDao;

    /**
     * 회사_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<CompanyResponse.Paging> selectCompanyPaging(PageRequest<CompanyRequest.PagingFilter> pageRequest) {
        return companyDao.selectCompanyPaging(pageRequest);
    }

    /**
     * 회사_수정
     *
     * @param updateCompanyRequest
     * @return
     */
    public Integer updateCompany(CompanyRequest.Update updateCompanyRequest) {
        return companyDao.updateCompany(updateCompanyRequest);
    }

    /**
     * 회사_등록
     *
     * @param updateCompanyRequest
     * @return
     */
    public Integer insertCompany(CompanyRequest.Insert updateCompanyRequest) {
        return companyDao.insertCompany(updateCompanyRequest);
    }

    /**
     * 회사_삭제
     *
     * @param deleteCompanyRequest
     * @return
     */
    public Integer deleteCompany(CompanyRequest.Delete deleteCompanyRequest) {
        return companyDao.deleteCompany(deleteCompanyRequest);
    }
}
