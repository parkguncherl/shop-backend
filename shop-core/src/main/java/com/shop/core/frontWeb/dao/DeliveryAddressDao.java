package com.shop.core.frontWeb.dao;

import com.shop.core.entity.DeliveryAddress;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryAddressDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.DeliveryAddress.";

    public List<DeliveryAddress> selectList(Long socialAccountId) {
        return sqlSession.selectList(NAMESPACE + "selectList", socialAccountId);
    }

    public DeliveryAddress selectById(Long id) {
        return sqlSession.selectOne(NAMESPACE + "selectById", id);
    }

    public int insert(DeliveryAddress deliveryAddress) {
        return sqlSession.insert(NAMESPACE + "insert", deliveryAddress);
    }

    public int update(DeliveryAddress deliveryAddress) {
        return sqlSession.update(NAMESPACE + "update", deliveryAddress);
    }

    public int clearDefault(Long socialAccountId) {
        return sqlSession.update(NAMESPACE + "clearDefault", socialAccountId);
    }

    public int delete(Long id) {
        return sqlSession.update(NAMESPACE + "delete", id);
    }
}
