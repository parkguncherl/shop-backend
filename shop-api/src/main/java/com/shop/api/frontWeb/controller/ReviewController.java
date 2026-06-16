package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.ReviewService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.Review;
import com.shop.core.frontWeb.vo.request.ReviewRequest;
import com.shop.core.frontWeb.vo.response.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frontWeb/review")
@Tag(name = "ReviewController", description = "FO 상품 리뷰 API")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @NotAuthRequired
    @PostMapping
    @Operation(summary = "리뷰 작성")
    public ApiResponse<ReviewResponse.Info> createReview(@RequestBody ReviewRequest.Create request) {
        return new ApiResponse<>(reviewService.createReview(request));
    }

    @NotAuthRequired
    @PutMapping("/{id}")
    @Operation(summary = "리뷰 수정")
    public ApiResponse<ReviewResponse.Info> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewRequest.Update request) {
        request.setId(id);
        return new ApiResponse<>(reviewService.updateReview(request));
    }

    @NotAuthRequired
    @DeleteMapping("/{id}")
    @Operation(summary = "리뷰 삭제")
    public ApiResponse<Void> deleteReview(
            @PathVariable Long id,
            @Parameter(description = "소셜 계정 ID") @RequestParam Long socialAccountId) {
        reviewService.deleteReview(id, socialAccountId);
        return new ApiResponse<>();
    }

    @NotAuthRequired
    @GetMapping("/product/{productId}")
    @Operation(summary = "상품 리뷰 목록 조회")
    public ApiResponse<ReviewResponse.ProductList> getProductReviews(@PathVariable Long productId) {
        return new ApiResponse<>(reviewService.getProductReviews(productId));
    }

    @NotAuthRequired
    @GetMapping("/order-item/{orderItemId}")
    @Operation(summary = "주문 아이템 리뷰 조회")
    public ApiResponse<Review> getReviewByOrderItemId(@PathVariable Long orderItemId) {
        return new ApiResponse<>(reviewService.getReviewByOrderItemId(orderItemId));
    }

    @NotAuthRequired
    @GetMapping("/my")
    @Operation(summary = "내 리뷰 목록 조회")
    public ApiResponse<List<ReviewResponse.MyItem>> getMyReviews(
            @Parameter(description = "소셜 계정 ID") @RequestParam Long socialAccountId) {
        return new ApiResponse<>(reviewService.getMyReviews(socialAccountId));
    }
}
