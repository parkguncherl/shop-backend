package com.shop.core.frontWeb.dao;

import com.shop.core.entity.Order;
import com.shop.core.entity.OrderDelivery;
import com.shop.core.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.Order.";

    public int insertOrder(Order order) {
        return sqlSession.insert(NAMESPACE + "insertOrder", order);
    }

    public int insertOrderItem(OrderItem orderItem) {
        return sqlSession.insert(NAMESPACE + "insertOrderItem", orderItem);
    }

    public int insertOrderDelivery(OrderDelivery orderDelivery) {
        return sqlSession.insert(NAMESPACE + "insertOrderDelivery", orderDelivery);
    }

    public Order selectOrderById(Long orderId) {
        return sqlSession.selectOne(NAMESPACE + "selectOrderById", orderId);
    }

    public Order selectOrderByOrderNo(String orderNo) {
        return sqlSession.selectOne(NAMESPACE + "selectOrderByOrderNo", orderNo);
    }

    public List<OrderItem> selectOrderItems(Long orderId) {
        return sqlSession.selectList(NAMESPACE + "selectOrderItems", orderId);
    }

    public OrderDelivery selectOrderDelivery(Long orderId) {
        return sqlSession.selectOne(NAMESPACE + "selectOrderDelivery", orderId);
    }

    public List<Order> selectOrdersBySocialAccountId(Long socialAccountId) {
        return sqlSession.selectList(NAMESPACE + "selectOrdersBySocialAccountId", socialAccountId);
    }

    public Order selectOrderByOrderItemId(Long orderItemId) {
        return sqlSession.selectOne(NAMESPACE + "selectOrderByOrderItemId", orderItemId);
    }

    public int updateOrderStatus(Long orderId, String orderStatus) {
        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus(orderStatus);
        return sqlSession.update(NAMESPACE + "updateOrderStatus", order);
    }
}
