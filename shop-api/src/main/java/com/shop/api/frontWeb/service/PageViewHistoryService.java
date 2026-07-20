package com.shop.api.frontWeb.service;

import com.shop.core.frontWeb.dao.PageViewHistoryDao;
import com.shop.core.frontWeb.vo.response.PageViewHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageViewHistoryService {

    private final PageViewHistoryDao pageViewHistoryDao;

    public List<PageViewHistoryResponse.RecentProduct> getRecentProducts(String guestId) {
        return pageViewHistoryDao.selectRecentProducts(guestId);
    }
}
