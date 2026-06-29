package com.shop.api.biz.receiving.service;

import com.shop.core.biz.receiving.dao.ReceivingDao;
import com.shop.core.biz.receiving.vo.request.ReceivingRequest;
import com.shop.core.biz.receiving.vo.response.ReceivingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceivingService {

    private final ReceivingDao receivingDao;

    public List<ReceivingResponse.ReceivingItem> getReceivingList(ReceivingRequest.ListFilter filter) {
        if (filter.getToDate() != null) {
            filter.setToDate(filter.getToDate().plusDays(1));
        }
        return receivingDao.selectReceivingList(filter);
    }

    public List<ReceivingResponse.ProductDetSearchItem> getProductDetSearchList(ReceivingRequest.ProductDetSearchFilter filter) {
        return receivingDao.selectProductDetSearchList(filter);
    }

    public int insertReceiving(ReceivingRequest.InsertReceiving request) {
        return receivingDao.insertReceiving(request);
    }

    public int updateReceiving(ReceivingRequest.UpdateReceiving request) {
        return receivingDao.updateReceiving(request);
    }

    public int deleteReceiving(ReceivingRequest.DeleteReceiving request) {
        return receivingDao.deleteReceiving(request);
    }
}
