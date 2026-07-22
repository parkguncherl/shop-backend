package com.shop.core.biz.system.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.UserRequest;
import com.shop.core.biz.system.vo.response.UserResponse;
import com.shop.core.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * Description: 계정 Dao
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDao {

    private final String PRE_NS = "com.shop.mapper.User.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 계정관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<UserResponse.Paging> selectUserPaging(PageRequest<UserRequest.PagingFilter> pageRequest) {
        List<UserResponse.Paging> users = sqlSession.selectList(PRE_NS.concat("selectUserPaging"), pageRequest);
        if (users != null && !users.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), users, users.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 계정_조회 (by Id)
     *
     * @param userId
     * @return
     */
    public UserResponse.SelectByLoginId selectUserById(Integer userId) {
        return sqlSession.selectOne(PRE_NS.concat("selectUserById"), userId);
    }


    /**
     * 계정_조회 (by Id)
     *
     * @param userId
     * @return
     */
    public Integer selectPartnerIdByUserId(Integer userId) {
        return sqlSession.selectOne(PRE_NS.concat("selectPartnerIdByUserId"), userId);
    }

    /**
     * 계정_조회 (by Id)
     *
     * @param loginId
     * @return
     */
    public Integer selectPartnerIdByLoginId(String loginId) {
        return sqlSession.selectOne(PRE_NS.concat("selectPartnerIdByLoginId"), loginId);
    }

    /**
     * 계정관리_조회 (by LoginId)
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectUserByLoginIdForLogin(String loginId) {
        return sqlSession.selectOne(PRE_NS.concat("selectUserByLoginIdForLogin"), loginId);
    }

    /**
     * 계정관리_조회 (by LoginId)
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectUserByLoginId(String loginId) {
        return sqlSession.selectOne(PRE_NS.concat("selectUserByLoginId"), loginId);
    }


    /**
     * 계정관리_조회 (by LoginId, countryCode)
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectUserByLoginIdCountryCode(String loginId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginId", loginId);
        return sqlSession.selectOne(PRE_NS.concat("selectUserByLoginIdCountryCode"), hashMap);
    }

    /**
     * 삭제된_최신_계정관리_조회 (by LoginId)
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectDelUserByLoginId(String loginId) {
        return sqlSession.selectOne(PRE_NS.concat("selectDelUserByLoginId"), loginId);
    }


    /**
     * 계정_조회 (by Uk)
     *
     * @param user
     * @return
     */
    public User selectUserByUk(User user) {
        return sqlSession.selectOne(PRE_NS.concat("selectUserByUk"), user);
    }

    /**
     * 계정_등록
     *
     * @param user
     * @return
     */
    public Integer insertUser(User user) {
        return sqlSession.insert(PRE_NS.concat("insertUser"), user);
    }

    /**
     * 계정_수정
     *
     * @param user
     * @return
     */
    public Integer updateUser(User user) {
        return sqlSession.update(PRE_NS.concat("updateUser"), user);
    }


    /**
     * 계정_삭제
     *
     * @param user
     * @return
     */
    public Integer deleteUser(User user) {
        return sqlSession.delete(PRE_NS.concat("deleteUser"), user);
    }

    /**
     * OTP번호 변경
     *
     * @param user
     * @return
     */
    public Integer updateOtpNo(User user) {
        return sqlSession.update(PRE_NS.concat("updateOtpNo"), user);
    }

    /**
     * update pass
     *
     * @param user
     * @return
     */
    public Integer updatePassword(User user) {
        return sqlSession.update(PRE_NS.concat("updatePassword"), user);
    }


    /**
     * update pass change tm
     *
     * @param user
     * @return
     */
    public Integer stayPassword(User user) {
        return sqlSession.update(PRE_NS.concat("stayPassword"), user);
    }

    /**
     * 계정_잠금해제
     *
     * @param user
     * @return
     */
    public Integer updateUserUnLock(User user) {
        return sqlSession.update(PRE_NS.concat("updateUserUnLock"), user);
    }

    /**
     * 계정_비밀번호초기화
     *
     * @param user
     * @return
     */
    public Integer updatePasswordInit(User user) {
        return sqlSession.update(PRE_NS.concat("updatePasswordInit"), user);
    }


    /**
     * 계정 목록조회 (designer 조회)
     * @param partnerId
     * @return
     */
    public List<User> selectDesinerUserList(Integer partnerId, String authCd) {
        Map<String, Object> params = new HashMap<>();
        params.put("partnerId", partnerId);
        params.put("authCd", authCd);

        return sqlSession.selectList(PRE_NS.concat("selectDesinerUserList"), params);
    }

}
