package com.shop.api.biz.mis.service;

import com.shop.core.biz.mis.dao.MisDao;
import com.shop.core.biz.mis.vo.request.MisRequest;
import com.shop.core.biz.mis.vo.response.MisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MisService {

    private final MisDao misDao;

    public List<MisResponse.ProductViewItem> getProductViewList(MisRequest.ListFilter filter) {
        filter.setToDate(filter.getToDate().plusDays(1));
        return misDao.selectProductViewList(filter);
    }

    public List<MisResponse.SalesStatItem> getSalesStatList(MisRequest.SalesStatFilter filter) {
        filter.setToDate(filter.getToDate().plusDays(1));
        return misDao.selectSalesStatList(filter);
    }

    public List<MisResponse.ProductViewItem> getSalesStatDetailList(MisRequest.SalesStatDetailFilter filter) {
        return misDao.selectSalesStatDetailList(filter);
    }

    public List<MisResponse.CategoryViewItem> getCategoryViewList(MisRequest.CategoryViewFilter filter) {
        filter.setToDate(filter.getToDate().plusDays(1));
        return misDao.selectCategoryViewList(filter);
    }
}
