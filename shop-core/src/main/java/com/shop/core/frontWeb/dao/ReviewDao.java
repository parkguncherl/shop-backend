package com.shop.core.frontWeb.dao;

import com.shop.core.entity.Review;
import com.shop.core.frontWeb.vo.request.ReviewRequest;
import com.shop.core.frontWeb.vo.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NS = "com.shop.mapper.frontWeb.Review.";

    public int insertReview(Review review) {
        return sqlSession.insert(NS + "insertReview", review);
    }

    public Review selectReviewById(Long id) {
        return sqlSession.selectOne(NS + "selectReviewById", id);
    }

    public Review selectReviewByOrderItemId(Long orderItemId) {
        return sqlSession.selectOne(NS + "selectReviewByOrderItemId", orderItemId);
    }

    public List<ReviewResponse.ProductItem> selectReviewsByProductId(Long productId) {
        return sqlSession.selectList(NS + "selectReviewsByProductId", productId);
    }

    public ReviewResponse.ProductList selectProductReviewSummary(Long productId) {
        return sqlSession.selectOne(NS + "selectProductReviewSummary", productId);
    }

    public List<ReviewResponse.MyItem> selectMyReviews(Long socialAccountId) {
        return sqlSession.selectList(NS + "selectMyReviews", socialAccountId);
    }

    public int updateReview(ReviewRequest.Update request) {
        return sqlSession.update(NS + "updateReview", request);
    }

    public int deleteReview(Long id) {
        return sqlSession.update(NS + "deleteReview", id);
    }
}
