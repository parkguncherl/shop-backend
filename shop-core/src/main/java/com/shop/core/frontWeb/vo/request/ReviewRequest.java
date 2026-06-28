package com.shop.core.frontWeb.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class ReviewRequest {

    @Getter
    @Setter
    @Schema(name = "ReviewRequestCreate", description = "리뷰 등록 요청", type = "object")
    public static class Create {
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
    }

    @Getter
    @Setter
    @Schema(name = "ReviewRequestUpdate", description = "리뷰 수정 요청", type = "object")
    public static class Update {
        private Long id;
        private Long socialAccountId;
        private Integer rating;
        private String content;
        private Long fileId;
        private String myFit;
        private String myWeight;
        private String myHeight;
    }
}
