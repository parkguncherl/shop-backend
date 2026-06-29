package com.shop.core.biz.receiving.dao;

import com.shop.core.biz.receiving.vo.request.ReceivingRequest;
import com.shop.core.biz.receiving.vo.response.ReceivingResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
@RequiredArgsConstructor
public class ReceivingDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.biz.Receiving.";

    public List<ReceivingResponse.ReceivingItem> selectReceivingList(ReceivingRequest.ListFilter filter) {
        return sqlSession.selectList(NAMESPACE + "selectReceivingList", filter);
    }

    public List<ReceivingResponse.ProductDetSearchItem> selectProductDetSearchList(ReceivingRequest.ProductDetSearchFilter filter) {
        return sqlSession.selectList(NAMESPACE + "selectProductDetSearchList", filter);
    }

    public int insertReceiving(ReceivingRequest.InsertReceiving request) {
        return sqlSession.insert(NAMESPACE + "insertReceiving", request);
    }

    public int updateReceiving(ReceivingRequest.UpdateReceiving request) {
        return sqlSession.update(NAMESPACE + "updateReceiving", request);
    }

    public int updateReceivingIfExist(ReceivingRequest.UpdateReceiving request) {
        return sqlSession.update(NAMESPACE + "updateReceivingIfExist", request);
    }

    public int deleteReceiving(ReceivingRequest.DeleteReceiving request) {
        return sqlSession.update(NAMESPACE + "deleteReceiving", request);
    }
}
