package com.binblur.core.biz.system.dao;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.vo.request.UserRequest;
import com.binblur.core.biz.system.vo.response.UserResponse;
import com.binblur.core.entity.User;
import com.binblur.core.entity.UserExpire;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


/**
 * <pre>
 * Description : 계정 Dao
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
@Repository
public class UserDao {

    private final String PRE_NS = "com.binblur.mapper.user.";

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;

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
     * 계정_등록
     *
     * @param user
     * @return
     */
    public Integer createUser(User user) {
        return sqlSession.insert(PRE_NS.concat("createUser"), user);
    }

    /**
     * 계정_조회 (by Id)
     *
     * @param userId
     * @return
     */
    public User selectUserById(Integer userId) {
        return sqlSession.selectOne(PRE_NS.concat("selectUserById"), userId);
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
    public UserResponse.SelectByLoginId selectUserByLoginIdCountryCode(String loginId, String countryCode) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("countryCode", countryCode);
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
     * 180일 경과조회 계정_조회 (by Uk)
     *
     * @param loginId
     * @return
     */
    public String checkExpireDayByLoginId(String loginId) {
        return sqlSession.selectOne(PRE_NS.concat("checkExpireDayByLoginId"), loginId);
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
        return sqlSession.update(PRE_NS.concat("deleteUser"), user);
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
     * 계정 잠금처리(이름등 업데이트)
     *
     * @param id
     * @return
     */
    public Integer updateExpireUser(Integer id) {
        return sqlSession.update(PRE_NS.concat("updateExpireUser"), id);
    }


    /**
     * 계정 잠금대상 정보 조회 계정 잠금처리
     *
     * @param expireDay
     * @return
     */
    public List<UserResponse.SelectExfireUser> selectExpireUser(Long expireDay) {
        return sqlSession.selectList(PRE_NS.concat("selectExpireUser"), expireDay);
    }

    /**
     * 계정_잠금_대상_조회 (by Uk)
     *
     * @param userExpire
     * @return
     */
    public UserExpire selectExpireUserByUk(UserExpire userExpire) {
        return sqlSession.selectOne(PRE_NS.concat("selectExpireUserByUk"), userExpire);
    }

    /**
     * 휴면계정 생성
     *
     * @param id
     * @return
     */
    public Integer insertExpireUser(Integer id) {
        return sqlSession.update(PRE_NS.concat("insertExpireUser"), id);
    }


    /**
     * 휴면계정 삭제
     *
     * @param id
     * @return
     */
    public Integer deleteExpireUser(Integer id) {
        return sqlSession.update(PRE_NS.concat("deleteExpireUser"), id);
    }

    /**
     * 탈퇴(삭제)계정 생성
     *
     * @param id
     * @return
     */
    public Integer insertOutUser(Integer id) {
        return sqlSession.insert(PRE_NS.concat("insertOutUser"), id);
    }


    /**
     * OTP_실패_카운트_증가
     *
     * @param user
     * @return
     */
    public Integer updateOtpFailCnt(User user) {
        return sqlSession.update(PRE_NS.concat("updateOtpFailCnt"), user);
    }

}
