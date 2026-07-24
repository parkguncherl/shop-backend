package com.shop.core.product.vo.request;

import com.shop.core.entity.CategoryProduct;
import com.shop.core.entity.Product;
import com.shop.core.entity.ProductDet;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Schema(name = "ProductMngRequest", description = "상품관리 영역 요청")
public class ProductMngRequest {

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestProductInfoFilter", description = "상품 정보 필터")
    public static class ProductInfoFilter implements RequestFilter {

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "파트너 id")
        private Integer partnerId;

        @Schema(description = "vendor id")
        private Integer vendorId;

        @Schema(description = "전시여부 (Y/N/null=전체)")
        private String showYn;

        /* 계절 필터: 선택된 계절만 'Y' 로 전달된다. 복수 선택 시 OR 조건(하나라도 포함되면 조회) */
        @Schema(description = "봄 포함 여부 (Y/null)")
        private String isSpring;

        @Schema(description = "여름 포함 여부 (Y/null)")
        private String isSummer;

        @Schema(description = "가을 포함 여부 (Y/null)")
        private String isAutumn;

        @Schema(description = "겨울 포함 여부 (Y/null)")
        private String isWinter;
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestProductDetInfoFilter", description = "상품 상세 정보 필터")
    public static class ProductDetInfoFilter implements RequestFilter {

        @Schema(description = "productId")
        private Integer prodId;

        @Schema(description = "상품상세컬러")
        private String prodDetColor;
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestCategoryProductInfoFilter", description = "카테고리 연결상품정보 필터")
    public static class CategoryProductInfoFilter implements RequestFilter {

        @Schema(description = "대응되는 category id")
        private Integer categoryId;
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestInsertProduct", description = "상품 추가 dto")
    public static class InsertProduct extends Product {
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestInsertProductDet", description = "상품상세 추가 dto")
    public static class InsertProductDet extends ProductDet {
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestInsertProduct", description = "상품정보(with productDet) 추가 dto")
    public static class InsertProductInfo extends InsertProduct {

        @Schema(description = "추가하고자 하는 productDet dto 에 대응하는 field")
        private InsertProductDet productDet;

        @Schema(description = "상품 생성 시 연결할 카테고리 id (선택)")
        private Integer categoryId;
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestUpdateProduct", description = "상품 수정 dto")
    public static class UpdateProduct extends Product {
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestUpdateProductDet", description = "상품상세 수정 dto")
    public static class UpdateProductDet extends ProductDet {
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestDeleteProduct", description = "상품 삭제 dto")
    public static class DeleteProduct {

        @Schema(description = "아이디(PK)")
        private Integer id;

        @Schema(description = "수정자")
        private String updUser;
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestDeleteProductDet", description = "상품상세 삭제 dto")
    public static class DeleteProductDet {

        @Schema(description = "아이디(PK)")
        private Integer id;

        @Schema(description = "수정자")
        private String updUser;
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestProductInfoWithExclusionFilter", description = "제외를 포함하는 상품 정보 필터")
    public static class ProductInfoWithExclusionFilter implements RequestFilter {

        @Schema(description = "상품명")
        private String prodNm;

        @Schema(description = "카테고리 id(lower partnerCode id)")
        private Integer categoryId;
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestInsertCategoryProduct", description = "카테고리 연결상품 추가 요청")
    public static class InsertCategoryProduct extends CategoryProduct {
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestUpdateCategoryProduct", description = "카테고리 연결상품 수정 요청")
    public static class UpdateCategoryProduct extends CategoryProduct {
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestDeleteCategoryProduct", description = "카테고리 연결상품 삭제 요청")
    public static class DeleteCategoryProduct {

        @Schema(description = "아이디(PK)")
        private Integer id;

        @Schema(description = "수정자")
        private String updUser;
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestUpdateCategoryProductSeq", description = "카테고리 연결상품(categoryProduct) seq 수정 요청")
    public static class UpdateCategoryProductSeq {

        @Schema(description = "대상 요소의 기존 seq")
        private Integer fromSeq;

        @Schema(description = "대상 요소가 지망하는 seq")
        private Integer toSeq;

        @Schema(description = "(연결상품의 부분집합을 식별 가능토록 하는) 카테고리 id")
        private Integer categoryId;
    }
}
