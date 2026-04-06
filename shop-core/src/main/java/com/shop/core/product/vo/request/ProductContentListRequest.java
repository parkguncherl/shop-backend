package com.shop.core.product.vo.request;

import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.entity.Contents;
import com.shop.core.entity.ContentsProduct;
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

    @Getter
    @Setter
    @Schema(name = "ProductContentListRequestProductInfoListFilter", description = "상품정보 목록 필터")
    public static class ProductInfoListFilter implements RequestFilter {

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "컨텐츠 id(해당 id에 대응되지 아니하는 (혹은 애초에 컨텐츠와 관계가 부재한)행을 조회하기 위한 인자(컨텐츠 id에 대응되는 prod 는 이미 추가되었다 여기어 마땅하므로)")
        private Integer contentsId;


        @Schema(description = "lastId")
        private Integer lastId;
    }

    @Getter
    @Setter
    @Schema(name = "ProductContentListRequestContentsProductInfoListFilter", description = "연결상품정보 목록 필터")
    public static class ContentsProductInfoListFilter implements RequestFilter {

        @Schema(description = "컨텐츠 id")
        private Integer contentsId;

    }

    @Getter
    @Setter
    @Schema(name = "ProductContentListRequestInsertContentsProduct", description = "연결상품 추가 요청")
    public static class InsertContentsProduct extends ContentsProduct {
    }

    @Getter
    @Setter
    @Schema(name = "ProductContentListRequestUpdateContentsProduct", description = "연결상품 수정 요청")
    public static class UpdateContentsProduct extends ContentsProduct {
    }

    @Getter
    @Setter
    @Schema(name = "ProductContentListRequestUpdateContentsProductSeq", description = "연결상품 seq 수정 요청")
    public static class UpdateContentsProductSeq {

        @Schema(description = "대상 요소의 기존 seq")
        private Integer fromSeq;

        @Schema(description = "대상 요소가 지망하는 seq")
        private Integer toSeq;

        @Schema(description = "컨텐츠 id")
        private Integer contentsId;
    }
}
