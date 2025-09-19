package com.shop.core.biz.mypage.dao;

import com.shop.core.biz.mypage.vo.request.FavoritesRequest;
import com.shop.core.biz.mypage.vo.response.FavoritesResponse;
import com.shop.core.entity.Favorites;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <pre>
 * Description: 계정 Dao
 * Date: 2023/01/26 12:35 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Repository
@RequiredArgsConstructor
public class FavoritesDao {

    private final String PRE_NS = "com.shop.mapper.mypage.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 계정관리_목록_조회 (페이징)
     *
     * @param id
     * @return
     */
    public List<FavoritesResponse.SelectFavorites> selectFavorites(FavoritesRequest.FavoritesListRequest favoritesListRequest) {
        return sqlSession.selectList(PRE_NS.concat("selectFavorites"), favoritesListRequest);
    }

    /**
     * 즐겨찾기 등록
     *
     * @param favorites
     * @return
     */
    public Integer insertFavorites(Favorites favorites) {
        return sqlSession.insert(PRE_NS.concat("insertFavorites"), favorites);
    }

    /**
     * 즐겨찾기 삭제
     *
     * @param favorites
     */
    public void deleteFavorites(Favorites favorites) {
        sqlSession.insert(PRE_NS.concat("deleteFavorites"), favorites);
    }

    /**
     * 즐겨찾기 삭제
     *
     * @param favorites
     */
    public void deleteFavoritesAll(Favorites favorites) {
        sqlSession.insert(PRE_NS.concat("deleteFavoritesAll"), favorites);
    }
}
