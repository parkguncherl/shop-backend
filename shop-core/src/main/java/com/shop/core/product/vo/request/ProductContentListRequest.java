package com.shop.core.product.vo.request;

import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.entity.Contents;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "ProductContentListRequest", description = "상품컨텐츠 목록 영역 요청")
public class ProductContentListRequest {

    @Getter
    @Setter
    @Schema(name = "ProductContentListRequestProductContentListFilter", description = "상품컨텐츠 목록 필터")
    public static class ProductContentListFilter implements RequestFilter {

        @Schema(description = "파트너 id")
        private Integer partnerId;

        @Schema(description = "컨텐츠 유형(전역 상수로 지정)")
        private String newsType;

        @Schema(description = "제목")
        private String newsTitle;


        @Schema(description = "lastId")
        private Integer lastId;
    }

    @Getter
    @Setter
    @Schema(name = "ProductContentListRequestInsertProductContents", description = "상품컨텐츠 추가 요청")
    public static class InsertProductContents extends Contents {

        @Schema(description = "업로드 파일 목록")
        private CommonRequest.FileUploads CommonRequestFileUploads; // 존재할 시 파일(이미지) 업로딩 요청도 포함된 걸로 간주
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ProductContentListRequestDeleteProductContents", description = "Contents 삭제 dto")
    public static class DeleteProductContents extends Contents {
    }
}
