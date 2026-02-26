package com.shop.core.product.vo.request;

import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.entity.Contents;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

// todo 상품컨텐츠목록으로의 이전이 완료되고 또한 해당 페이지를 더 이상 사용하지 않는다 여기어질 시 삭제하기

@Schema(name = "ProductContentsRequest", description = "상품컨텐츠 영역 요청")
public class ProductContentsRequest {

    @Getter
    @Setter
    @Schema(name = "ProductContentsRequestInsertProductContents", description = "상품컨텐츠 추가 요청")
    public static class InsertProductContents extends Contents {

        @Schema(description = "업로드 파일 목록")
        private CommonRequest.FileUploads CommonRequestFileUploads;
    }
}
