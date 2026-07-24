package com.shop.core.biz.vendorProduct.dao;

import com.shop.core.biz.vendorProduct.vo.request.VendorProductRequest;
import com.shop.core.biz.vendorProduct.vo.response.VendorProductResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VendorProductDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.VendorProduct.";

    /**
     * 협력업체(vendorId)에 속한 유효한 상품 목록을 조회합니다.
     *
     * @param filter 협력업체 ID / 파트너 ID
     * @return 상품명, 시즌, 색상, 사이즈 목록
     */
    public List<VendorProductResponse> selectVendorProductList(VendorProductRequest.Filter filter) {
        return sqlSession.selectList(NAMESPACE.concat("selectVendorProductList"), filter);
    }
}
