package com.shop.api.product.service;

import com.shop.core.product.dao.ProductMngDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Description: ProductMng Service
 * Date: 2026/01/30 14:23
 * Author: park junsung
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductMngService {

    private final ProductMngDao productMngDao;

    /**
     * 페이징된 공장(생산처) 기본정보 목록을 조회합니다.
     * @param pageRequest 페이징 및 필터 정보를 포함한 요청 객체
     * @return 페이징된 기본정보 목록 응답
     */
//    public PageResponse<FactoryResponse.DefInfoPaging> selectFactoryDefInfoPaging(PageRequest<FactoryRequest.DefInfoPagingFilter> pageRequest) {
//        return factoryDao.selectFactoryDefInfoPaging(pageRequest);
//    }
}
