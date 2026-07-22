package com.shop.api.biz.system.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.dao.UserDao;
import com.shop.core.biz.system.vo.request.UserRequest;
import com.shop.core.biz.system.vo.response.UserResponse;
import com.shop.core.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.List;


/**
 * <pre>
 * Description: 계정 Service
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserDao userDao;


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
     * 파트너id_조회 (by loginId)
     *
     * @param loginId
     * @return
     */
    public Integer selectPartnerIdByLoginId(String loginId) {
        return userDao.selectPartnerIdByLoginId(loginId);
    }

    /**
     * 계정관리_조회 (by LoginId) 비번 안나옴
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectUserByLoginId(String loginId) {
        return userDao.selectUserByLoginId(loginId);
    }


    /**
     * 계정관리_조회 (by LoginId) 비번 나옴
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectUserByLoginIdForLogin(String loginId) {
        UserResponse.SelectByLoginId result = userDao.selectUserByLoginIdForLogin(loginId);
        return result;
    }

    /**
     * 계정관리_조회 (by LoginId, countryCode)
     *
     * @param loginId
     * @return
     */
    public UserResponse.SelectByLoginId selectUserByLoginIdCountryCode(String loginId) {
        return userDao.selectUserByLoginIdCountryCode(loginId);
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
     * 계정_조회 (by Uk)
     *
     * @param user
     * @return
     */
    public User selectUserByUk(User user) {
        return userDao.selectUserByUk(user);
    }

    /**
     * 계정_등록
     *
     * @param userRequest
     * @return
     */
    public Integer insertUser(UserRequest.Create userRequest) {
        Integer resultCnt = 0;
        User insertUser = userRequest.toEntity();
        resultCnt=  userDao.insertUser(insertUser);
        return resultCnt;
    }

    /**
     * 계정_수정
     *
     * @param userRequest
     * @return
     */
    public Integer updateUser(UserRequest.Update userRequest) {
        return userDao.updateUser(userRequest.toEntity());
    }

    /**
     * 계정_삭제
     *
     * @param userRequest
     * @return
     */
    public Integer deleteUser(UserRequest.Delete userRequest) {
        User user = selectUserById(userRequest.getId());
        //userDao.insertOutUser(user);  // 삭제테이블에 추가
        Integer rtnCount = userDao.deleteUser(userRequest.toEntity());
        return rtnCount;
    }

    /**
     * 계정_otp 업데이트
     *
     * @param user
     * @return
     */
    public Integer updateOtpNo(User user) {
        return userDao.updateOtpNo(user);
    }

    /**
     * 비밀번호 업데이트
     *
     * @param user
     * @return
     */
    public Integer updatePassword(User user) {
        return userDao.updatePassword(user);
    }


    /**
     * 비밀번호 최종벼경일자만 업데이트
     *
     * @param user
     * @return
     */
    public Integer stayPassword(User user) {
        return userDao.stayPassword(user);
    }


    /**
     * 계정_잠금해제
     *
     * @param userRequest
     * @return
     */
    public Integer updateUserUnLock(UserRequest.UnLock userRequest) {
        return userDao.updateUserUnLock(userRequest.toEntity());
    }

    /**
     * 계정_비밀번호초기화
     *
     * @param userRequest
     * @return
     */
    public Integer updatePasswordInit(UserRequest.PasswordInit userRequest) {
        return userDao.updatePasswordInit(userRequest.toEntity());
    }

    /**
     * 계정 목록조회 (designer 조회)
     * @param partnerId
     * @return
     */
    public List<User> selectDesinerUserListByAuth350(Integer partnerId) {
        return userDao.selectDesinerUserList(partnerId, "350");
    }

}
