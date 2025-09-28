package com.shop.api.biz.mypage.service;

import com.shop.core.biz.mypage.dao.FavoritesDao;
import com.shop.core.biz.mypage.dao.MypageDao;
import com.shop.core.biz.mypage.vo.request.FavoritesRequest;
import com.shop.core.biz.mypage.vo.response.FavoritesResponse;
import com.shop.core.entity.Favorites;
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

}
