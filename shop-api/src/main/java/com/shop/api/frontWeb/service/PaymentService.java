package com.shop.api.frontWeb.service;

import com.shop.core.entity.Payment;
import com.shop.core.entity.PaymentDet;
import com.shop.core.frontWeb.dao.PaymentDao;
import com.shop.core.frontWeb.vo.request.PaymentRequest;
import com.shop.core.frontWeb.vo.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentDao paymentDao;

    @Transactional
    public PaymentResponse.Info createPayment(PaymentRequest.Create request) {
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .orderNo(request.getOrderNo())
                .paymentId(request.getPaymentId())
                .paymentStatus("P")
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .cancelAmount(0L)
                .delYn("N")
                .build();
        paymentDao.insertPayment(payment);

        if (request.getDetails() != null) {
            for (PaymentRequest.CreateDet requestDet : request.getDetails()) {
                PaymentDet det = PaymentDet.builder()
                        .paymentId(payment.getId())
                        .paymentNo(requestDet.getPaymentNo())
                        .orderId(payment.getOrderId())
                        .orderNo(payment.getOrderNo())
                        .payType(requestDet.getPayType())
                        .payMethod(requestDet.getPayMethod())
                        .paymentStatus("P")
                        .amount(requestDet.getAmount())
                        .pgProvider(requestDet.getPgProvider())
                        .pgTid(requestDet.getPgTid())
                        .portonePaymentId(requestDet.getPortonePaymentId())
                        .rawResponse(requestDet.getRawResponse())
                        .cancelAmount(0L)
                        .delYn("N")
                        .build();
                paymentDao.insertPaymentDet(det);
            }
        }

        return getPayment(payment.getId());
    }

    public PaymentResponse.Info getPayment(Long paymentId) {
        Payment payment = paymentDao.selectPaymentById(paymentId);
        if (payment == null) return null;
        return toInfo(payment);
    }

    public PaymentResponse.Info getPaymentByOrderNo(String orderNo) {
        Payment payment = paymentDao.selectPaymentByOrderNo(orderNo);
        if (payment == null) return null;
        return toInfo(payment);
    }

    public List<PaymentResponse.ListItem> getPaymentList(Long socialAccountId) {
        return paymentDao.selectPaymentListBySocialAccountId(socialAccountId);
    }

    private PaymentResponse.Info toInfo(Payment payment) {
        PaymentResponse.Info info = new PaymentResponse.Info();
        info.setPaymentSeq(payment.getId());
        info.setOrderId(payment.getOrderId());
        info.setOrderNo(payment.getOrderNo());
        info.setPaymentId(payment.getPaymentId());
        info.setPaymentStatus(payment.getPaymentStatus());
        info.setTotalAmount(payment.getTotalAmount());
        info.setCurrency(payment.getCurrency());
        info.setPaidTm(payment.getPaidTm());

        List<PaymentResponse.Det> details = new ArrayList<>();
        for (PaymentDet paymentDet : paymentDao.selectPaymentDets(payment.getId())) {
            PaymentResponse.Det det = new PaymentResponse.Det();
            det.setPaymentDetId(paymentDet.getId());
            det.setPaymentId(paymentDet.getPaymentId());
            det.setPaymentNo(paymentDet.getPaymentNo());
            det.setPayType(paymentDet.getPayType());
            det.setPayMethod(paymentDet.getPayMethod());
            det.setPaymentStatus(paymentDet.getPaymentStatus());
            det.setAmount(paymentDet.getAmount());
            det.setPgProvider(paymentDet.getPgProvider());
            det.setPgTid(paymentDet.getPgTid());
            det.setPortonePaymentId(paymentDet.getPortonePaymentId());
            det.setApprovedTm(paymentDet.getApprovedTm());
            details.add(det);
        }
        info.setDetails(details);
        return info;
    }
}
