package com.shop.core.biz.mypage.dao;

import com.shop.core.biz.mypage.vo.request.FavoritesRequest;
import com.shop.core.biz.mypage.vo.response.FavoritesResponse;
import com.shop.core.biz.mypage.vo.response.MypageResponse;
import com.shop.core.entity.PartnerPrint;
import com.shop.core.entity.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <pre>
 * Description: 마이페이지 Dao
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class MypageDao {

    private final String PRE_NS = "com.shop.mapper.mypage.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 계정관리_목록_조회 (페이징)
     *
     * @param favoritesListRequest
     * @return
     */
    public List<FavoritesResponse.SelectFavorites> selectFavorites(FavoritesRequest.FavoritesListRequest favoritesListRequest) {
        return sqlSession.selectList(PRE_NS.concat("selectFavorites"), favoritesListRequest);
    }

    /**
     * 사용자 전표양식 조회
     * @param partnerId
     * @return
     */
    public MypageResponse.SelectPartnerPrint selectPartnerPrint(Integer partnerId) {
        return sqlSession.selectOne(PRE_NS.concat("selectPartnerPrint"), partnerId);
    }

    /**
     * 사용자 전표양식 생성
     * @param mypageRequest
     * @return
     */
    public Integer insertPartnerPrint(PartnerPrint mypageRequest) {
        return sqlSession.insert(PRE_NS.concat("insertPartnerPrint"), mypageRequest);
    }

    /**
     * 사용자 전표양식 삭제 (by partnerId)
     *
     * @param partnerId
     * @return
     */
    public Integer deletePartnerPrint(Integer partnerId) {
        return sqlSession.update(PRE_NS.concat("deletePartnerPrint"), partnerId);
    }

    /**
     * 사용자 전표양식 수정
     *
     * @param request
     * @return
     */
    public Integer updatePartnerPrint(PartnerPrint request) {
        return sqlSession.update(PRE_NS.concat("updatePartnerPrint"), request);
    }

    /**
     * 스티커 데이터 생성
     * @param request
     * @return
     */
    public Integer insertStickerData(Notice request) {
        return sqlSession.insert(PRE_NS.concat("insertStickerData"), request);
    }
}
