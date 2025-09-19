package com.shop.core.biz.system.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.UserRequest;
import com.shop.core.biz.system.vo.response.UserResponse;
import com.shop.core.entity.User;
import com.shop.core.entity.UserExpire;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    private final String PRE_NS = "com.shop.mapper.user.";

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
     * 파트너ID 수정
     *
     * @param user
     * @return
     */
    public Integer updateUserPartnerId(User user) {
        return sqlSession.update(PRE_NS.concat("updateUserPartnerId"), user);
    }


    /**
     * 영업일 변경
     *
     * @param user
     * @return
     */
    public Integer updateUserWorkYmd(User user) {
        return sqlSession.update(PRE_NS.concat("updateUserWorkYmd"), user);
    }

    /**
     * 파트너의 생성시간 조회
     *
     * @param userId 사용자 ID (Integer 타입)
     * @return LocalDateTime 파트너 생성시간
     */
    public LocalDateTime getPartnerCreatedTime(Integer userId) {
        return sqlSession.selectOne(PRE_NS.concat("getPartnerCreatedTime"), userId);
    }

    /**
     * 영업일 변경
     *
     * @param user
     * @return
     */
    public Integer updateUserLogisId(User user) {
        return sqlSession.update(PRE_NS.concat("updateUserLogisId"), user);
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
    public List<UserResponse.SelectExfireUser> selectExpireUser(Integer expireDay) {
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
     * @param user
     * @return
     */
    public Integer insertOutUser(User user) {
        return sqlSession.insert(PRE_NS.concat("insertOutUser"), user);
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

    /**
     * 계정 목록조회 (물류계정 500번대 조회)
     * @return
     */
    public List<User> selectInstockUserList(Integer workLogisId) {
        return sqlSession.selectList(PRE_NS.concat("selectInstockUserList"), workLogisId);
    }



    /**
     * 탈퇴(삭제)계정 생성
     *
     * @param userId
     * @param LoginId
     * @return
     */
    public Integer createAuthForPartner(Integer userId, String LoginId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("creUsr", LoginId);
        return sqlSession.insert(PRE_NS.concat("createAuthForPartner"), params);
    }

    /**
     * 탈퇴(삭제)계정 생성
     *
     * @param userId
     * @param LoginId
     * @return
     */
    public Integer updateAuthForDelete(Integer userId, String LoginId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("updUsr", LoginId);
        return sqlSession.insert(PRE_NS.concat("updateAuthForDelete"), params);
    }

}
