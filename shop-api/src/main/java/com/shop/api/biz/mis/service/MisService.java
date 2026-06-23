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
        LocalDate fromDate = filter.getFromDate() != null ? filter.getFromDate() : LocalDate.now();
        LocalDate toDate   = filter.getToDate()   != null ? filter.getToDate()   : LocalDate.now();

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime   = toDate.plusDays(1).atStartOfDay();

        return misDao.selectProductViewList(fromDateTime, toDateTime);
    }
}
