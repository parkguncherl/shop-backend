package com.shop.core.biz.mypage.dao;

import com.shop.core.biz.mypage.vo.request.FavoritesRequest;
import com.shop.core.biz.mypage.vo.response.FavoritesResponse;
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
 * Author: luckeey
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

}
