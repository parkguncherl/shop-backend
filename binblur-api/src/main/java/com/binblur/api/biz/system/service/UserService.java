package com.binblur.api.biz.system.service;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.dao.UserDao;
import com.binblur.core.biz.system.vo.request.UserRequest;
import com.binblur.core.biz.system.vo.response.UserResponse;
import com.binblur.core.entity.User;
import com.binblur.core.entity.UserExpire;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <pre>
 * Description : 계정 Service
 * Date : 2023/01/26 12:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
@Slf4j
@Service
@Transactional(transactionManager = "dataTxManager")
public class UserService {

    @Autowired
    private UserDao userDao;


    /**
     * 계정관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<UserResponse.Paging> selectUserPaging(PageRequest<UserRequest.PagingFilter> pageRequest) {
        return userDao.selectUserPaging(pageRequest);
    }

    /**
     * 계정_조회 (by Id)
     *
     * @param userId
     * @return
     */
    public User selectUserById(Integer userId) {
        return userDao.selectUserById(userId);
    }

    /**
     * 계정관리_조회 (by LoginId)
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectUserByLoginId(String loginId) {
        return userDao.selectUserByLoginId(loginId);
    }


    /**
     * 계정관리_조회 (by LoginId, countryCode)
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectUserByLoginIdCountryCode(String loginId, String countryCode) {
        return userDao.selectUserByLoginIdCountryCode(loginId, countryCode);
    }

    /**
     * 삭제된_최신_계정관리_조회 (by LoginId)
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectDelUserByLoginId(String loginId) {
        return userDao.selectDelUserByLoginId(loginId);
    }

    /**
     * 계정180일 미로그인 조회 (by LoginId)
     *
     * @param loginId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public String checkExpireDayByLoginId(String loginId) {
        return userDao.checkExpireDayByLoginId(loginId);
    }

    /**
     * 계정_조회 (by Uk)
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User selectUserByUk(User user) {
        return userDao.selectUserByUk(user);
    }

    /**
     * 계정_등록
     *
     * @param userRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer insertUser(UserRequest.Create userRequest) {
        return userDao.insertUser(userRequest.toEntity());
    }

    /**
     * 계정_수정
     *
     * @param userRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateUser(UserRequest.Update userRequest) {
        return userDao.updateUser(userRequest.toEntity());
    }

    /**
     * 계정_삭제
     *
     * @param userRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer deleteUser(UserRequest.Delete userRequest) {
        userDao.insertOutUser(userRequest.getId()); // 삭제테이블에 넎는다.
        Integer rtnCount = userDao.deleteUser(userRequest.toEntity());
        return rtnCount;
    }

    /**
     * 계정_권한조회
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String getUserAuth(User user) {
        // 권한을 조회한다.
        return userDao.selectUserById(user.getId()).getAuthCd();
    }

    /**
     * 계정_otp 업데이트
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateOtpNo(User user) {
        return userDao.updateOtpNo(user);
    }

    /**
     * 비밀번호 업데이트
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updatePassword(User user) {
        return userDao.updatePassword(user);
    }


    /**
     * 비밀번호 최종벼경일자만 업데이트
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer stayPassword(User user) {
        return userDao.stayPassword(user);
    }


    /**
     * 계정_잠금해제
     *
     * @param userRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateUserUnLock(UserRequest.UnLock userRequest) {
        // 계정_잠금_대상_조회 (by Uk)
        UserExpire userExpire = userDao.selectExpireUserByUk(userRequest.toUserExpire());

        if (userExpire != null) {
            userDao.deleteExpireUser(userExpire.getUserId());
            userRequest.setUserNm(userExpire.getUserNm());
        }

        return userDao.updateUserUnLock(userRequest.toEntity());
    }

    /**
     * 계정_비밀번호초기화
     *
     * @param userRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updatePasswordInit(UserRequest.PasswordInit userRequest) {
        return userDao.updatePasswordInit(userRequest.toEntity());
    }

    /**
     * OTP_실패_카운트_증가
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateOtpFailCnt(User user) {
        return userDao.updateOtpFailCnt(user);
    }

}
