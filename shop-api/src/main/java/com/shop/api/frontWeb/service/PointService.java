package com.shop.api.frontWeb.service;

import com.shop.core.entity.PointHistory;
import com.shop.core.frontWeb.dao.PointDao;
import com.shop.core.frontWeb.vo.response.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public PointResponse.HistoryList getHistories(Long socialAccountId) {
        List<PointHistory> entities = pointDao.selectPointHistories(socialAccountId);

        List<PointResponse.History> histories = entities.stream().map(e -> {
            PointResponse.History h = new PointResponse.History();
            h.setId(e.getId());
            h.setSocialAccountId(e.getSocialAccountId());
            h.setOrderId(e.getOrderId());
            h.setPaymentId(e.getPaymentId());
            h.setPointType(e.getPointType());
            h.setPointAmount(e.getPointAmount());
            h.setDescription(e.getDescription());
            h.setCreTm(e.getCreTm());
            return h;
        }).toList();

        PointResponse.HistoryList result = new PointResponse.HistoryList();
        result.setSocialAccountId(socialAccountId);
        result.setPointBalance(pointDao.selectPointBalance(socialAccountId));
        result.setHistories(histories);
        return result;
    }
}
