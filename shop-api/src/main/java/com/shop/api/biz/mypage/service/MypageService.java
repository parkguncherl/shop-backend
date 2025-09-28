package com.binblur.api.biz.mypage.service;

import com.binblur.api.biz.partner.service.PartnerService;
import com.binblur.core.biz.mypage.dao.FavoritesDao;
import com.binblur.core.biz.mypage.dao.MypageDao;
import com.binblur.core.biz.mypage.vo.request.FavoritesRequest;
import com.binblur.core.biz.mypage.vo.request.MypageRequest;
import com.binblur.core.biz.mypage.vo.response.FavoritesResponse;
import com.binblur.core.biz.mypage.vo.response.MypageResponse;
import com.binblur.core.biz.partner.dao.PartnerDao;
import com.binblur.core.entity.Favorites;
import com.binblur.core.entity.Partner;
import com.binblur.core.entity.User;
import com.binblur.core.enums.ApiResultCode;
import com.binblur.core.enums.GlobalConst;
import com.binblur.core.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * Description: 마이페이지 Service
 * Date: 2024/06/24 2:16 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MypageService {

    private final FavoritesDao favoritesDao;
    private final MypageDao mypageDao;
    private final PartnerDao partnerDao;

    /**
     * 즐겨찾기
     * @param id
     */
    public List<FavoritesResponse.SelectFavorites> selectFavorites(Integer id, String authCd) {
        FavoritesRequest.FavoritesListRequest favoritesListRequest = new FavoritesRequest.FavoritesListRequest();
        favoritesListRequest.setId(id);
        favoritesListRequest.setAuthCd(authCd);
        return favoritesDao.selectFavorites(favoritesListRequest);
    }

    /**
     * 즐겨찾기 등록
     * @param favorites
     */
    public Integer insertFavorites(Favorites favorites) {
        return favoritesDao.insertFavorites(favorites);
    }


    /**
     * 즐겨찾기 삭제
     *
     * @param favorites
     */
    public void deleteFavorites(Favorites favorites) {
        favoritesDao.deleteFavorites(favorites);
    }


    /**
     * 즐겨찾기 삭제
     * @param favorites
     */
    public void deleteFavoritesAll(Favorites favorites) {
        favoritesDao.deleteFavoritesAll(favorites);
    }

    /**

    /**
     * 사용자 전표양식 조회
     * @param partnerId
     * @return ApiResponse
     */
    public MypageResponse.SelectPartnerPrint selectPartnerPrint(Integer partnerId) {
        return mypageDao.selectPartnerPrint(partnerId);
    }

    /**
     * 사용자 전표양식 생성
     *
     * @param partner
     * @param jwtUser
     * @return
     */
    public Integer insertPartnerPrint(Partner partner, User jwtUser) {
        // 기본값 세팅
        MypageRequest.PartnerPrintRequest mypageRequest = new MypageRequest.PartnerPrintRequest();
        mypageRequest.setPartnerId(partner.getId());
        mypageRequest.setLogoprintyn(GlobalConst.LOGO_PRINT_YN.getCode());
        mypageRequest.setLogoLocCd(GlobalConst.LOGO_LOC_CD.getCode());
        mypageRequest.setTitleYn(GlobalConst.TITLE_YN.getCode());
        mypageRequest.setTitleMng(GlobalConst.TITLE_MNG.getCode());
        mypageRequest.setTitleNor(GlobalConst.TITLE_NOR.getCode());
        mypageRequest.setTopYn(GlobalConst.TOP_YN.getCode());
        mypageRequest.setTopMng(GlobalConst.TOP_MNG.getCode());
        mypageRequest.setTopNor(GlobalConst.TOP_NOR.getCode());
        mypageRequest.setBottomYn(GlobalConst.BOTTOM_YN.getCode());
        mypageRequest.setBottomMng(GlobalConst.BOTTOM_MNG.getCode());
        mypageRequest.setBottomNor(GlobalConst.BOTTOM_NOR.getCode());
        mypageRequest.setCreUser(jwtUser.getLoginId());
        return mypageDao.insertPartnerPrint(mypageRequest.toEntity());
    }

    /**
     * 사용자 전표양식 삭제 (by partnerId)
     *
     * @param partnerId
     * @return
     */
    public Integer deletePartnerPrint(Integer partnerId) {
        return mypageDao.deletePartnerPrint(partnerId);
    }

    /**
     * 사용자 전표양식 수정
     *
     * @param request
     * @return
     */
    public Integer updatePartnerPrint(MypageRequest.MypagePrintSetUpdateRequest request) {
        // 동일상품 SKU 정보나 혼용율 정보 있으면 partner수정
        if(request.getCompPrnCd() != null || request.getSamplePrnYn() != null){
            Partner partner = Partner.builder()
                    .id(request.getPartnerId())
                    .compPrnCd(request.getCompPrnCd())
                    .samplePrnYn(request.getSamplePrnYn())
                    .build();

            Integer result = partnerDao.updatePartner(partner);
            if(result != 1){
                throw new CustomRuntimeException(ApiResultCode.FAIL, "파트너 전표설정 수정이 실패했습니다.");
            }
        }
        return mypageDao.updatePartnerPrint(request);
    }
    /**
     * 스티커 데이터 생성
     *
     * @param partner
     * @param jwtUser
     * @return
     */
    public Integer insertStickerData(Partner partner, User jwtUser) {
        MypageRequest.StickerDataRequest stickerRequest = new MypageRequest.StickerDataRequest();
        stickerRequest.setNoticeCd(partner.getId().toString());
        stickerRequest.setTitle(partner.getPartnerNm());
        stickerRequest.setNoticeCntn("매장 간단한 정보를 공유하세요...");
        stickerRequest.setCreUser(jwtUser.getLoginId());
        return mypageDao.insertStickerData(stickerRequest.toEntity());
    }

    /**
     * 사용자 전표설정 조회
     * @param partnerId
     * @return ApiResponse
     */
    public MypageResponse.SelectPartnerPrintInfo selectPartnerPrintInfo(Integer partnerId) {
        Partner partner = partnerDao.selectPartnerById(partnerId);
        MypageResponse.SelectPartnerPrintInfo response = new MypageResponse.SelectPartnerPrintInfo();
        response.setCompPrnCd(partner.getCompPrnCd());
        response.setSamplePrnYn(partner.getSamplePrnYn());
        return response;
    }

    /**
     * 사용자 전표설정 수정
     * @param partner
     * @return ApiResponse
     */
    public Integer updatePartnerPrintInfo(Partner partner) {
        return partnerDao.updatePartner(partner);
    }
}
