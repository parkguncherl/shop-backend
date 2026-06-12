package com.shop.api.frontWeb.service;

import com.shop.core.frontWeb.dao.PointDao;
import com.shop.core.frontWeb.vo.response.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointDao pointDao;

    public PointResponse.Balance getBalance(Long socialAccountId) {
        PointResponse.Balance balance = new PointResponse.Balance();
        balance.setSocialAccountId(socialAccountId);
        balance.setPointBalance(pointDao.selectPointBalance(socialAccountId));
        return balance;
    }
}
