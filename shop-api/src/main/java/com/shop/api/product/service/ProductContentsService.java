package com.shop.api.product.service;

import com.shop.core.entity.User;
import com.shop.core.product.dao.ProductContentsDao;
import com.shop.core.product.vo.request.ProductContentsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * Description: ProductContents Service
 * Date: 2026/02/04
 * Author: park junsung
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductContentsService {

    private final ProductContentsDao productContentsDao;

    /**
     * 신규 상품컨텐츠 데이터 추가
     * @param insertProductContents
     * @return 추가된 행의 수
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer insertProductContents(ProductContentsRequest.InsertProductContents insertProductContents, User jwtUser) {
        // todo 파일 추가 영역도 지정
        return productContentsDao.insertProductContents(insertProductContents);
    }
}
