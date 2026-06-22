package com.shop.api.frontWeb.service;

import com.shop.core.biz.partner.dao.PartnerDao;
import com.shop.core.biz.partner.vo.response.PartnerResponse;
import com.shop.core.entity.Order;
import com.shop.core.entity.PointHistory;
import com.shop.core.entity.Review;
import com.shop.core.enums.OrderStatus;
import com.shop.core.enums.PointType;
import com.shop.core.frontWeb.dao.OrderDao;
import com.shop.core.frontWeb.dao.PointDao;
import com.shop.core.frontWeb.dao.ReviewDao;
import com.shop.core.frontWeb.vo.request.ReviewRequest;
import com.shop.core.frontWeb.vo.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private static final Integer DEFAULT_PARTNER_ID = 1;

    private final ReviewDao reviewDao;
    private final OrderDao orderDao;
    private final PointDao pointDao;
    private final PartnerDao partnerDao;

    @Transactional
    public ReviewResponse.Info createReview(ReviewRequest.Create request) {
        // 주문 아이템 → 주문 조회 후 본인 검증 및 결제 완료 확인
        Order order = orderDao.selectOrderByOrderItemId(request.getOrderItemId());
        if (order == null) {
            throw new IllegalArgumentException("주문 정보를 찾을 수 없습니다.");
        }
        if (!order.getSocialAccountId().equals(request.getSocialAccountId())) {
            throw new IllegalStateException("본인의 주문에만 리뷰를 작성할 수 있습니다.");
        }
        if (!OrderStatus.PAID.getCode().equals(order.getOrderStatus())) {
            throw new IllegalStateException("결제 완료된 주문에만 리뷰를 작성할 수 있습니다.");
        }

        // 중복 리뷰 확인
        Review existing = reviewDao.selectReviewByOrderItemId(request.getOrderItemId());
        if (existing != null) {
            throw new IllegalStateException("이미 작성한 리뷰가 있습니다.");
        }

        Review review = Review.builder()
                .socialAccountId(request.getSocialAccountId())
                .orderItemId(request.getOrderItemId())
                .productId(request.getProductId())
                .productDetId(request.getProductDetId())
                .rating(request.getRating())
                .content(request.getContent())
                .fileId(request.getFileId())
                .build();

        reviewDao.insertReview(review);

        // 리뷰 포인트 적립: 실카드결제금액(포인트 제외) × review_point_rate / 100
        PartnerResponse.Select partner = partnerDao.selectPartnerDet(DEFAULT_PARTNER_ID);
        int rate = (partner != null && partner.getReviewPointRate() != null) ? partner.getReviewPointRate() : 0;
        java.math.BigDecimal baseAmount = java.math.BigDecimal.valueOf(
                order.getPaymentAmount() != null ? order.getPaymentAmount() : 0L);
        java.math.BigDecimal earnPoint = baseAmount
                .multiply(java.math.BigDecimal.valueOf(rate))
                .divide(java.math.BigDecimal.valueOf(100), java.math.RoundingMode.DOWN);

        if (earnPoint.compareTo(java.math.BigDecimal.ZERO) > 0) {
            pointDao.insertPointHistory(PointHistory.builder()
                    .socialAccountId(request.getSocialAccountId())
                    .pointType(PointType.REVIEW)
                    .pointAmount(earnPoint.longValue())
                    .description("리뷰 작성 포인트 적립")
                    .build());
        }

        return toInfo(reviewDao.selectReviewById(review.getId()));
    }

    @Transactional
    public ReviewResponse.Info updateReview(ReviewRequest.Update request) {
        Review review = reviewDao.selectReviewById(request.getId());
        if (review == null) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }
        if (!review.getSocialAccountId().equals(request.getSocialAccountId())) {
            throw new IllegalStateException("본인의 리뷰만 수정할 수 있습니다.");
        }

        reviewDao.updateReview(request);
        return toInfo(reviewDao.selectReviewById(request.getId()));
    }

    @Transactional
    public void deleteReview(Long id, Long socialAccountId) {
        Review review = reviewDao.selectReviewById(id);
        if (review == null) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }
        if (!review.getSocialAccountId().equals(socialAccountId)) {
            throw new IllegalStateException("본인의 리뷰만 삭제할 수 있습니다.");
        }
        reviewDao.deleteReview(id);
    }

    public ReviewResponse.ProductList getProductReviews(Long productId) {
        ReviewResponse.ProductList summary = reviewDao.selectProductReviewSummary(productId);
        if (summary == null) {
            summary = new ReviewResponse.ProductList();
            summary.setProductId(productId);
            summary.setAvgRating(0.0);
            summary.setReviewCount(0L);
        }
        summary.setReviews(reviewDao.selectReviewsByProductId(productId));
        return summary;
    }

    public Review getReviewByOrderItemId(Long orderItemId) {
        return reviewDao.selectReviewByOrderItemId(orderItemId);
    }

    public List<ReviewResponse.MyItem> getMyReviews(Long socialAccountId) {
        return reviewDao.selectMyReviews(socialAccountId);
    }

    private ReviewResponse.Info toInfo(Review r) {
        ReviewResponse.Info info = new ReviewResponse.Info();
        info.setId(r.getId());
        info.setSocialAccountId(r.getSocialAccountId());
        info.setOrderItemId(r.getOrderItemId());
        info.setProductId(r.getProductId());
        info.setProductDetId(r.getProductDetId());
        info.setRating(r.getRating());
        info.setContent(r.getContent());
        info.setFileId(r.getFileId());
        info.setIsBlinded(r.getIsBlinded());
        info.setCreTm(r.getCreTm());
        info.setUptTm(r.getUptTm());
        return info;
    }
}
