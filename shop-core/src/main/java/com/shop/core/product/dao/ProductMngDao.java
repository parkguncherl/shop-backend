package com.shop.core.product.dao;

import com.shop.core.product.vo.request.ProductMngRequest;
import com.shop.core.product.vo.response.ProductMngResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description: ProductMng Dao
 * Date: 2026/01/30
 * Author: park junsung
 * </pre>
 */
@Mapper
@Repository
@RequiredArgsConstructor
public class ProductMngDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.shop.mapper.product.productMng.";

    /**
     * 상품관리-상품정보 조회
     * @param productInfoFilter
     * @return ProductInfo List
     */
    public List<ProductMngResponse.ProductInfo> selectProdInfoList(ProductMngRequest.ProductInfoFilter productInfoFilter) {
        return sqlSession.selectList(NAMESPACE + "selectProdInfoList", productInfoFilter);
    }

    /**
     * 상품관리-상품정보 상세 조회
     * @param productDetInfoFilter
     * @return ProductDetInfo List
     */
    public List<ProductMngResponse.ProductDetInfo> selectProdDetInfo(ProductMngRequest.ProductDetInfoFilter productDetInfoFilter) {
        return sqlSession.selectList(NAMESPACE + "selectProdDetInfo", productDetInfoFilter);
    }

    /**
     * 상품관리-상품정보 추가
     * @param insertProduct
     * @return inserted row's cnt
     */
    public int insertProduct(ProductMngRequest.InsertProduct insertProduct) {
        return sqlSession.insert(NAMESPACE + "insertProduct", insertProduct);
    }

    /**
     * 상품관리-상품상세정보 추가
     * @param insertProductDet
     * @return inserted row's cnt
     */
    public int insertProductDet(ProductMngRequest.InsertProductDet insertProductDet) {
        return sqlSession.insert(NAMESPACE + "insertProductDet", insertProductDet);
    }

    /**
     * 상품관리-상품정보 수정
     * @param updateProduct
     * @return updated row's cnt
     */
    public int updateProduct(ProductMngRequest.UpdateProduct updateProduct) {
        return sqlSession.update(NAMESPACE + "updateProduct", updateProduct);
    }

    /**
     * 상품관리-상품상세정보 수정
     * @param updateProductDet
     * @return updated row's cnt
     */
    public int updateProductDet(ProductMngRequest.UpdateProductDet updateProductDet) {
        return sqlSession.update(NAMESPACE + "updateProductDet", updateProductDet);
    }

    /**
     * 상품관리-상품정보 삭제
     * @param deleteProduct
     * @return deleted row's cnt
     */
    public int deleteProduct(ProductMngRequest.DeleteProduct deleteProduct) {
        return sqlSession.update(NAMESPACE + "deleteProduct", deleteProduct);
    }

    /**
     * 상품관리-상품상세정보 삭제
     * @param deleteProductDet
     * @return deleted row's cnt
     */
    public int deleteProductDet(ProductMngRequest.DeleteProductDet deleteProductDet) {
        return sqlSession.update(NAMESPACE + "deleteProductDet", deleteProductDet);
    }

    /**
     * 신규 카테고리 연결상품 데이터 추가
     * @param insertCategoryProduct
     * @return 추가된 행의 수
     */
    public int insertCategoryProduct(ProductMngRequest.InsertCategoryProduct insertCategoryProduct) {
        return sqlSession.insert(NAMESPACE + "insertCategoryProduct", insertCategoryProduct);
    }

    /**
     * 기존 카테고리 연결상품 데이터 수정
     * @param updateCategoryProduct
     * @return 추가된 행의 수
     */
    public int updateCategoryProduct(ProductMngRequest.UpdateCategoryProduct updateCategoryProduct) {
        return sqlSession.update(NAMESPACE + "insertCategoryProduct", updateCategoryProduct);
    }
}
