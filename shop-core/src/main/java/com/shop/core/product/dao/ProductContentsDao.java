package com.shop.core.product.dao;

import com.shop.core.entity.Contents;
import com.shop.core.product.vo.request.ProductContentsRequest;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

// todo 상품컨텐츠목록으로의 이전이 완료되고 또한 해당 페이지를 더 이상 사용하지 않는다 여기어질 시 삭제하기

/**
 * <pre>
 * Description: ProductContents Dao
 * Date: 2026/02/04
 * Author: park junsung
 * </pre>
 */
@Mapper
@Repository
@RequiredArgsConstructor
public class ProductContentsDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.shop.mapper.product.productContents.";

    /**
     * 신규 상품컨텐츠 데이터 추가
     * @param contents
     * @return 추가된 행의 수
     */
    public int insertProductContents(Contents contents) {
        return sqlSession.insert(NAMESPACE.concat("insertProductContents"), contents);
    }
}
