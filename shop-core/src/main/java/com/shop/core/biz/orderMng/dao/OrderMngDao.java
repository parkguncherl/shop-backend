package com.shop.core.biz.orderMng.dao;

import com.shop.core.biz.orderMng.vo.response.OrderMngResponse;
import com.shop.core.frontWeb.vo.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class OrderMngDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.biz.OrderMng.";

    /** BO 주문 목록 조회 (기간별) */
    public List<OrderMngResponse.BoListItem> selectOrderListForBo(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return sqlSession.selectList(NAMESPACE + "selectOrderListForBo",
                Map.of("fromDateTime", fromDateTime, "toDateTime", toDateTime));
    }

    /** BO 주문 상세 조회 */
    public OrderResponse.Info selectOrderDetailForBo(Long orderId) {
        return sqlSession.selectOne(NAMESPACE + "selectOrderDetailForBo", orderId);
    }
}
