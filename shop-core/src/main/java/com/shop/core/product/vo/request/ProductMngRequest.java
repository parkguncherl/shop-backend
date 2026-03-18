package com.shop.core.product.vo.request;

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
    }

    @Getter
    @Setter
    @Schema(name = "ProductMngRequestUpdateProduct", description = "상품 수정 dto")
    public static class UpdateProduct extends Product {
    }
}
