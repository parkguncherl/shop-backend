package com.binblur.core.biz.system.dao;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.vo.request.AttributeRequest;
import com.binblur.core.biz.system.vo.request.MenuRequest;
import com.binblur.core.biz.system.vo.response.AttributeResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description : 속성_관리 Dao
 * Company : home
 * Author : park
 * </pre>
 */
@Repository
public class AttributeDao {

    private final String PRE_NS = "com.binblur.mapper.attribute.";

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession; // MybatisAutoConfiguration.sqlSessionTemplate

    /**
     * 속성_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */

    public PageResponse<AttributeResponse.Paging> selectAttributePaging(PageRequest<AttributeRequest.PagingFilter> pageRequest) {
        List<AttributeResponse.Paging> Attributes = sqlSession.selectList(PRE_NS.concat("selectAttributeListPaging"), pageRequest);
        if (Attributes != null && !Attributes.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), Attributes, Attributes.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 속성_추가
     *
     * @param insertRequest
     * @return
     */

    public Integer insertAttribute(List<AttributeRequest.Insert> insertRequest) {
        return sqlSession.insert(PRE_NS.concat("insertAttribute"), insertRequest);
    }

    /**
     * 속성_수정
     *
     * @param updateRequest
     * @return
     */

    public Integer updateAttribute(List<AttributeRequest.Update> updateRequest) {
        return sqlSession.update(PRE_NS.concat("updateAttribute"), updateRequest);
    }
}
