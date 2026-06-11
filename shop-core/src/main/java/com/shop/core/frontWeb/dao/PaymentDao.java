package com.shop.core.frontWeb.dao;

import com.shop.core.entity.Payment;
import com.shop.core.entity.PaymentDet;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.Payment.";

    public int insertPayment(Payment payment) {
        return sqlSession.insert(NAMESPACE + "insertPayment", payment);
    }

    public int insertPaymentDet(PaymentDet paymentDet) {
        return sqlSession.insert(NAMESPACE + "insertPaymentDet", paymentDet);
    }

    public Payment selectPaymentById(Long id) {
        return sqlSession.selectOne(NAMESPACE + "selectPaymentById", id);
    }

    public Payment selectPaymentByPaymentId(String paymentId) {
        return sqlSession.selectOne(NAMESPACE + "selectPaymentByPaymentId", paymentId);
    }

    public Payment selectPaymentByOrderNo(String orderNo) {
        return sqlSession.selectOne(NAMESPACE + "selectPaymentByOrderNo", orderNo);
    }

    public List<PaymentDet> selectPaymentDets(Long paymentId) {
        return sqlSession.selectList(NAMESPACE + "selectPaymentDets", paymentId);
    }

    public int updatePaymentStatus(Payment payment) {
        return sqlSession.update(NAMESPACE + "updatePaymentStatus", payment);
    }

    public int updatePaymentDetStatus(PaymentDet paymentDet) {
        return sqlSession.update(NAMESPACE + "updatePaymentDetStatus", paymentDet);
    }

    public int updatePaymentCancel(Payment payment) {
        return sqlSession.update(NAMESPACE + "updatePaymentCancel", payment);
    }

    public int updatePaymentDetCancel(PaymentDet paymentDet) {
        return sqlSession.update(NAMESPACE + "updatePaymentDetCancel", paymentDet);
    }
}
