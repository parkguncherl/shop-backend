package com.shop.core.frontWeb.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponse {

    @Getter
    @Setter
    @Schema(name = "ReviewResponseInfo", description = "리뷰 상세", type = "object")
    public static class Info {
        private Long id;
        private Long socialAccountId;
        private Long orderItemId;
        private Long productId;
        private Long productDetId;
        private Integer rating;
        private String content;
        private Long fileId;
        private String myFit;
        private String myWeight;
        private String myHeight;
        private String isBlinded;
        private LocalDateTime creTm;
        private LocalDateTime uptTm;
    }

    @Getter
    @Setter
    @Schema(name = "ReviewResponseProductItem", description = "상품 리뷰 목록 아이템", type = "object")
    public static class ProductItem {
        private Long id;
        private Long socialAccountId;
        private Integer rating;
        private String content;
        private Long fileId;
        private LocalDateTime creTm;
        private String fitGroup;
        private String myHeightWeightNm;
    }

    @Getter
    @Setter
    @Schema(name = "ReviewResponseProductList", description = "상품 리뷰 목록", type = "object")
    public static class ProductList {
        private Long productId;
        private Double avgRating;
        private Long reviewCount;
        private List<ProductItem> reviews;
    }

    @Getter
    @Setter
    @Schema(name = "ReviewResponseMyItem", description = "내 리뷰 목록 아이템", type = "object")
    public static class MyItem {
        private Long id;
        private Long orderItemId;
        private Long productId;
        private String productName;
        private Integer rating;
        private String content;
        private Long fileId;
        private String myFit;
        private String myWeight;
        private String myHeight;
        private LocalDateTime creTm;
    }
}
