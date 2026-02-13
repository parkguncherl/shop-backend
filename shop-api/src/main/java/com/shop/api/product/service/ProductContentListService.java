package com.shop.api.product.service;

import com.shop.api.biz.system.service.UserService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.User;
import com.shop.core.enums.GlobalConst;
import com.shop.core.product.dao.ProductContentListDao;
import com.shop.core.product.dao.ProductMngDao;
import com.shop.core.product.vo.request.ProductContentListRequest;
import com.shop.core.product.vo.request.ProductMngRequest;
import com.shop.core.product.vo.response.ProductContentListResponse;
import com.shop.core.product.vo.response.ProductMngResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Description: ProductContentList Service
 * Date: 2026/02/13
 * Author: park junsung
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductContentListService {

    private final UserService userService;
    private final ProductContentListDao productContentListDao;

    /**
     * 상품관리-상품컨텐츠목록 조회
     * @param pageRequest
     * @return 페이징된 ProductContent List
     */
    public PageResponse<ProductContentListResponse.ProductContent> selectProdContentList(PageRequest<ProductContentListRequest.ProductContentListFilter> pageRequest, User jwtUser) {
        pageRequest.getFilter().setPartnerId(userService.selectPartnerIdByLoginId(jwtUser.getLoginId()));
        pageRequest.getFilter().setNewsType(GlobalConst.PRODUCT_CONTENTS_NEWS_TYPE.getCode());
        return productContentListDao.selectProdContentList(pageRequest);
    }
}
