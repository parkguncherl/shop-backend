package com.shop.api.biz.partner.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.partner.dao.PartnerDao;
import com.shop.core.biz.partner.vo.request.PartnerRequest;
import com.shop.core.biz.partner.vo.response.PartnerResponse;
import com.shop.core.entity.Partner;
import com.shop.core.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PartnerService {

    private final PartnerDao partnerDao;

    /**
     * 내파트너만 조회하기
     */
    public PartnerResponse.Select selectMyPartnerByLoginId(String loginId) {
        return partnerDao.selectMyPartnerByLoginId(loginId);
    }

    /**
     * 화주관리_목록_조회 (리스트)
     *
     * @return
     */
    public List<PartnerResponse.Select> selectPartnerList(Integer logisId) {
        return partnerDao.selectPartnerList(logisId);
    }

    /**
     * 화주관리_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<PartnerResponse.Paging> selectPartnerPaging(PageRequest<PartnerRequest.PagingFilter> pageRequest) {
        return partnerDao.selectPartnerPaging(pageRequest);
    }

    /**
     * 화주관리_목록_조회 (검색)
     *
     * @return
     */
    public List<PartnerResponse.ForSearching> selectPartnerListForSearching(PartnerRequest.FilterForList filterForList, User jwtUser) {
        return partnerDao.selectPartnerListForSearching(filterForList);
    }


    /**
     * 화주_등록
     *
     * @param partnerRequest
     * @param jwtUser
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer insertPartner(PartnerRequest.Create partnerRequest, User jwtUser) {

        // 세션정보를 이용해서 등록자 세팅
        partnerRequest.setCreUser(jwtUser.getLoginId());
        Partner partner = partnerRequest.toEntity();
        return partnerDao.insertPartner(partner);
    }

    /**
     * 화주_조회 (by Id)
     *
     * @param partnerId
     * @return
     */
    public Partner selectPartnerById(Integer partnerId) {
        return partnerDao.selectPartnerById(partnerId);
    }


    /**
     * 화주_수정
     *
     * @param partnerRequest
     * @return
     */
    public Integer updatePartner(PartnerRequest.Update partnerRequest, User jwtUser) {
        // 세션정보를 이용해서 수정자 세팅
        partnerRequest.setUpdUser(jwtUser.getLoginId());
        return partnerDao.updatePartner(partnerRequest.toEntity());
    }


    /**
     * 화주_삭제
     *
     * @param partnerRequest
     */
    public void deletePartner(PartnerRequest.Delete partnerRequest) {
        Partner partner = new Partner();
        partner.setId(partnerRequest.getId());
        partnerDao.deletePartner(partner);
    }

}
