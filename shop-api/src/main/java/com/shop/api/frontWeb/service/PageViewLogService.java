package com.shop.api.frontWeb.service;

import com.shop.core.entity.PageViewLog;
import com.shop.core.frontWeb.dao.PageViewLogDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PageViewLogService {



    private static final Pattern PRODUCT_URL_PATTERN = Pattern.compile("/products/[^/]+/(\\d+)");

    private final PageViewLogDao pageViewLogDao;

    public void insertPageViewLog(PageViewLog pageViewLog) {
        // pageUrl에서 이전 상품 ID 추출 (/products/{category}/{id})
        if (pageViewLog.getPageUrl() != null && pageViewLog.getBefProductId() == null) {
            Matcher m = PRODUCT_URL_PATTERN.matcher(pageViewLog.getPageUrl());
            if (m.find()) {
                pageViewLog.setBefProductId(Integer.parseInt(m.group(1)));
            }
        }
        pageViewLogDao.insertPageViewLog(pageViewLog);
    }

    public void updatePageViewLog(PageViewLog pageViewLog) {
        pageViewLogDao.updatePageViewLog(pageViewLog);
    }

    public PageViewLog selectPageViewLogById(Long id) {
        return pageViewLogDao.selectPageViewLogById(id);
    }
}
