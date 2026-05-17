package com.shop.api.frontWeb.service;

import com.shop.core.entity.PageViewLog;
import com.shop.core.frontWeb.dao.PageViewLogDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageViewLogService {



    private final PageViewLogDao pageViewLogDao;

    public void insertPageViewLog(PageViewLog pageViewLog) {
        pageViewLogDao.insertPageViewLog(pageViewLog);
    }

    public void updatePageViewLog(PageViewLog pageViewLog) {
        pageViewLogDao.updatePageViewLog(pageViewLog);
    }

    public PageViewLog selectPageViewLogById(Long id) {
        return pageViewLogDao.selectPageViewLogById(id);
    }
}
