package com.smart.api.biz.mypage.service;

import com.smart.core.biz.system.dao.UserDao;
import com.smart.core.biz.system.vo.request.UserRequest;
import com.smart.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Description : 마이페이지 Service
 * Date : 2023/04/24 2:16 PM
 * Company : smart90
 * Author : dhkwon
 * </pre>
 */
@Slf4j
@Service
@Transactional(transactionManager = "dataTxManager")
public class MypageService {

    @Autowired
    private UserDao userDao;

    /**
     * 사용자 조회 (페이징)
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User selectUserById(Integer id) {
        return userDao.selectUserById(id);
    }

    /**
     * 사용자 수정
     * @param userRequest
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateUser(UserRequest.Update userRequest) {
        User user = userRequest.toEntity();
        return userDao.updateUser(user);
    }
}
