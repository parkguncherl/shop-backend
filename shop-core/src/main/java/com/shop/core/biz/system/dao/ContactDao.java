package com.shop.core.biz.system.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.ContactRequest;
import com.shop.core.biz.system.vo.response.ContactResponse;
import com.shop.core.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description: 접속로그_관리 Dao
 * Date: 2023/02/14 11:58 AM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Repository
@RequiredArgsConstructor
public class ContactDao {

    private final String PRE_NS = "com.binblur.mapper.contact.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 접속로그_조회 (페이징)
     * @param pageRequest
     * @return
     */
    public PageResponse<ContactResponse.Paging> selectContactListPaging(PageRequest<ContactRequest.PagingFilter> pageRequest) {
        List<ContactResponse.Paging> contacts = sqlSession.selectList(PRE_NS.concat("selectContactListPaging"), pageRequest);
        if ( contacts != null && !contacts.isEmpty() ) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), contacts, contacts.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 접속로그입력
     * @param contact
     * @return
     */
    public Integer insertContact(Contact contact) {
        return sqlSession.insert(PRE_NS.concat("insertContact"), contact);
    }

    /**
     * 접속로그 조회 (By Id)
     * @param contactId
     * @return
     */
    public Contact selectContactById(Integer contactId) {
        return sqlSession.selectOne(PRE_NS.concat("selectContactById"), contactId);
    }


    /**
     * * 2시간 이전거래로그 있는지 확인 없으면 logout  (By Id)
     * @param contactId
     * @return
     */
    public String isOverTime(Integer contactId) {
        return sqlSession.selectOne(PRE_NS.concat("isOverTime"), contactId);
    }


    /**
     * 접속로그삭제(배치잡)
     * @return
     */
    public Integer deleteContact() {
        return sqlSession.delete(PRE_NS.concat("deleteContact"));
    }

}
