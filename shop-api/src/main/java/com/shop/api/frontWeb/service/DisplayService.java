package com.shop.api.frontWeb.service;

import com.shop.core.biz.system.dao.PartnerCodeDao;
import com.shop.core.biz.system.vo.request.PartnerCodeRequest;
import com.shop.core.biz.system.vo.response.PartnerCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <pre>
 * Description: DisplayService
 * Date: 2026/03/09
 * Author: park gun cheol
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DisplayService {

    private final PartnerCodeDao partnerCodeDao;
    /**
     * 상품관리-상품컨텐츠목록 조회
     * @param partnerCodeRequest
     * @return ProductInfo List
     */
    public List<PartnerCodeResponse.PartnerCodeDropDown> selectLowerCodeByPartnerCodeUpper(PartnerCodeRequest.PartnerCodeDropDown partnerCodeRequest) {
        return partnerCodeDao.selectLowerCodeByPartnerCodeUpper(partnerCodeRequest);
    }

}
