package com.shop.core.frontWeb.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class ComuRequest {

    @Getter
    @Setter
    @Schema(name = "ComuRequestCreate", description = "상담 시작 요청 (유형 선택 시 자동 메시지 생성)", type = "object")
    public static class Create {
        @Schema(description = "소셜 계정 ID")
        private Long socialAccountId;

        @Schema(description = "상담 유형 코드 (code_upper=10130)")
        private String comuType;

        @Schema(description = "주문 ID")
        private Long orderId;
    }

    @Getter
    @Setter
    @Schema(name = "ComuRequestAddMessage", description = "메시지 추가 요청", type = "object")
    public static class AddMessage {
        @Schema(description = "소셜 계정 ID")
        private Long socialAccountId;

        @Schema(description = "메시지 내용")
        private String content;

        @Schema(description = "첨부 파일 ID")
        private Integer fileId;
    }

    @Getter
    @Setter
    @Schema(name = "ComuRequestProductQna", description = "상품 Q&A 질문 등록 요청", type = "object")
    public static class ProductQna {
        @Schema(description = "소셜 계정 ID")
        private Long socialAccountId;

        @Schema(description = "질문 내용")
        private String content;
    }

    @Getter
    @Setter
    @Schema(name = "ComuRequestAdminReply", description = "관리자 답변 요청", type = "object")
    public static class AdminReply {
        @Schema(description = "메시지 내용")
        private String content;

        @Schema(description = "첨부 파일 ID")
        private Integer fileId;
    }
}
