package com.shop.core.biz.system.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.AttributeRequest;
import com.shop.core.biz.system.vo.request.CompanyRequest;
import com.shop.core.biz.system.vo.request.MenuRequest;
import com.shop.core.biz.system.vo.response.AttributeResponse;
import com.shop.core.biz.system.vo.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description: 속성_관리 Dao
 * Company: home
 * Author : park
 * </pre>
 */
@Repository
@RequiredArgsConstructor
public class CompanyDao {

    private final String PRE_NS = "com.shop.mapper.company.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession; // MybatisAutoConfiguration.sqlSessionTemplate

    /**
     * 회사_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */

    public PageResponse<CompanyResponse.Paging> selectCompanyPaging(PageRequest<CompanyRequest.PagingFilter> pageRequest) {
        List<CompanyResponse.Paging> Company = sqlSession.selectList(PRE_NS.concat("selectCompanyListPaging"), pageRequest);
        if (Company != null && !Company.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), Company, Company.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 회사_수정
     *
     * @param updateRequest
     * @return
     */

    public Integer updateCompany(CompanyRequest.Update updateRequest) {
        return sqlSession.update(PRE_NS.concat("updateCompany"), updateRequest);
    }

    /**
     * 회사_등록
     *
     * @param insertRequest
     * @return
     */

    public Integer insertCompany(CompanyRequest.Insert insertRequest) {
        return sqlSession.update(PRE_NS.concat("insertCompany"), insertRequest);
    }

    /**
     * 회사_삭제
     *
     * @param deleteRequest
     * @return
     */

    public Integer deleteCompany(CompanyRequest.Delete deleteRequest) {
        return sqlSession.delete(PRE_NS.concat("deleteCompany"), deleteRequest);
    }
}
