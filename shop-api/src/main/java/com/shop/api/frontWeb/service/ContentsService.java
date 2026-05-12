package com.shop.api.frontWeb.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.frontWeb.dao.ContentsDao;
import com.shop.core.frontWeb.vo.request.ContentsRequest;
import com.shop.core.frontWeb.vo.response.ContentsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Description: ContentsService
 * Date: 2026/05/12
 * Author: park junsung
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContentsService {

    private final ContentsDao contentsDao;

    /**
     * frontWeb 이하 컨텐츠 목록 조회(when code etc of partner_code is 'M') 비즈니스 레이어
     * @param pageRequest
     * @return ContentsInfo PageResponse
     */
    public PageResponse<ContentsResponse.ContentsInfo> selectContentsInfoListPaging(PageRequest<ContentsRequest.ContentsInfoListFilter> pageRequest) {
        return contentsDao.selectContentsInfoListPaging(pageRequest);
    }
}
