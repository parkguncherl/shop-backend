package com.shop.core.biz.system.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.AuthRequest;
import com.shop.core.biz.system.vo.request.MenuRequest;
import com.shop.core.biz.system.vo.response.AuthResponse;
import com.shop.core.biz.system.vo.response.MenuResponse;
import com.shop.core.entity.Auth;
import com.shop.core.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Description: 메뉴_관리 Dao
 * Date: 2023/02/14 11:58 AM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Repository
@RequiredArgsConstructor
public class MenuDao {

    private final String PRE_NS = "com.binblur.mapper.menu.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 메뉴_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<MenuResponse.Paging> selectMenuListPaging(PageRequest<MenuRequest.PagingFilter> pageRequest) {
        List<MenuResponse.Paging> menus = sqlSession.selectList(PRE_NS.concat("selectMenuListPaging"), pageRequest);
        if (menus != null && !menus.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), menus, menus.get(0).getTotalRowCount());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }

    /**
     * 메뉴_리스트_조회 (by menuCd)
     *
     * @param menu
     * @return
     */
    public List<Menu> selectMenuList(Menu menu) {
        return sqlSession.selectList(PRE_NS.concat("selectMenuList"), menu);
    }


    /**
     * 메뉴_리스트_조회 (by 사용자별)
     *
     * @param menu
     * @return
     */
    public List<Menu> selectMenuListForUser(Menu menu) {
        return sqlSession.selectList(PRE_NS.concat("selectMenuListForUser"), menu);
    }


    /**
     * (인가 여부를 포함한)메뉴_리스트_조회 (by 사용자별)
     *
     * @param menu
     * @return
     */
    public List<MenuResponse.WithAuthSub> selectMenuListWithAuth(Menu menu) {
        return sqlSession.selectList(PRE_NS.concat("selectMenuListWithAuth"), menu);
    }


    
    
    /**
     * 하위_메뉴_리스트_조회 (by upMenuCd)
     *
     * @param menu
     * @return
     */
    public List<Menu> selectMenuListByUpMenuCd(Menu menu) {
        return sqlSession.selectList(PRE_NS.concat("selectMenuListByUpMenuCd"), menu);
    }

    /**
     * 메뉴별_권한_조회 (by menuCd)
     *
     * @param menuCd
     * @return
     */
    public List<AuthResponse.Entity> selectMenuAuthList(String menuCd) {
        return sqlSession.selectList(PRE_NS.concat("selectMenuAuthList"), menuCd);
    }

    /**
     * 메뉴_조회 (by Id)
     *
     * @param menuId
     * @return
     */
    public Menu selectMenuById(Integer menuId) {
        return sqlSession.selectOne(PRE_NS.concat("selectMenuById"), menuId);
    }

    /**
     * 메뉴_조회 (by uk)
     *
     * @param menu
     * @return
     */
    public Menu selectMenuByUK(Menu menu) {
        return sqlSession.selectOne(PRE_NS.concat("selectMenuByUK"), menu);
    }

    /**
     * 메뉴명 조회 (by meunNm)
     *
     * @param menu
     * @return
     */
    public Integer countMenuByNm(Menu menu) {
        return sqlSession.selectOne(PRE_NS.concat("countMenuByNm"), menu);
    }

    /**
     * 권한(기능)
     *
     * @param userId
     * @return
     */
    public String selectCustomRoles(Integer userId) {
        return sqlSession.selectOne(PRE_NS.concat("selectCustomRoles"), userId);
    }


    /**
     * 특정 사용자의 특정 권한(메뉴) 조회
     *
     * @param menu
     * @return
     */
    public Menu selectUserMenu(Menu menu) {
        return sqlSession.selectOne(PRE_NS.concat("selectUserMenu"), menu);
    }

    /**
     * 특정 사용자의 메뉴(권한) 정보 저장
     *
     * @param menu
     * @return
     */
    public Integer insertUserMenu(Menu menu) {
        return sqlSession.insert(PRE_NS.concat("insertUserMenu"), menu);
    }

    /**
     * 메뉴입력 ()
     *
     * @param createRequest
     * @return
     */
    public Integer insertMenu(MenuRequest.Create createRequest) {
        return sqlSession.insert(PRE_NS.concat("insertMenu"), createRequest);
    }

    /**
     * 메뉴_수정
     *
     * @param updateRequest
     * @return
     */
    public Integer updateMenu(MenuRequest.Update updateRequest) {
        return sqlSession.update(PRE_NS.concat("updateMenu"), updateRequest);
    }


    /**
     * (메뉴 목록)권한의 인가 여부 변경 (by 사용자별)
     *
     * @param withAuthSubElement
     * @return
     */
    public Integer updateUserMenuAuth(MenuRequest.WithAuthSubElement withAuthSubElement) {
        return sqlSession.update(PRE_NS.concat("updateUserMenuAuth"), withAuthSubElement);
    }

    /**
     * 메뉴_삭제
     *
     * @param menu
     * @return
     */
    public Integer deleteMenu(Menu menu) {
        return sqlSession.update(PRE_NS.concat("deleteMenu"), menu);
    }

    /**
     * 메뉴권한_삭제
     *
     * @param menuCd
     * @return
     */
    public Integer deleteMenuAuth(String menuCd) {
        return sqlSession.update(PRE_NS.concat("deleteMenuAuth"), menuCd);
    }

    /**
     * 메뉴_전체_삭제
     *
     * @return
     */
    public Integer deleteMenuAll() {
        return sqlSession.update(PRE_NS.concat("deleteMenuAll"), null);
    }

    /**
     * 메뉴권한_전체_삭제
     *
     * @return
     */
    public Integer deleteMenuAuthAll() {
        return sqlSession.update(PRE_NS.concat("deleteMenuAuthAll"), null);
    }

    /**
     * 메뉴입력 ()
     *
     * @param auth
     * @return
     */
    public Integer insertMenuAuth(Auth auth) {
        return sqlSession.insert(PRE_NS.concat("insertMenuAuth"), auth);
    }

    /**
     * 메뉴_권한_수정
     *
     * @param auth
     * @return
     */
    public Integer updateMenuAuth(Auth auth) {
        return sqlSession.update(PRE_NS.concat("updateMenuAuth"), auth);
    }

    /**
     * 메뉴_권한_조회 (by menuCd,authCd,...)
     *
     * @param auth
     * @return
     */
    public Auth selectMenuAuth(Auth auth) {
        return sqlSession.selectOne(PRE_NS.concat("selectMenuAuth"), auth);
    }

    /**
     * 메뉴_권한_조회 (by menuCd,authCd)
     *
     * @param auth
     * @return
     */
    public Auth selectMenuAuthByUk(Auth auth) {
        return sqlSession.selectOne(PRE_NS.concat("selectMenuAuthByUk"), auth);
    }

    /**
     * 상위_메뉴_목록_조회
     *
     * @return
     */
    public List<Menu> selectTopMenuList() {
        return sqlSession.selectList(PRE_NS.concat("selectTopMenuList"));
    }

    /**
     * 계정별 메뉴 권한 체크
     *
     * @param authCd
     * @param menuUri
     * @return
     */
    public Integer selectAuthMenuCount(String authCd, String menuUri) {
        Map<String, String> params = new HashMap<>();
        params.put("authCd", authCd);
        params.put("menuUri", menuUri);
        return sqlSession.selectOne(PRE_NS.concat("selectAuthMenuCount"), params);
    }

    /**
     * 계정별 메뉴 권한 플래그 조회
     *
     * @param authRequest
     * @return
     */
    public AuthResponse.MenuAuth selectMenuAuthYn(AuthRequest.MenuAuth authRequest) {
        return sqlSession.selectOne(PRE_NS.concat("selectMenuAuthYn"), authRequest);
    }


    /**
     * 계정별 메뉴 권한 플래그 조회 for oms
     *
     * @param authRequest
     * @return
     */
    public AuthResponse.MenuAuth selectMenuAuthYnForOms(AuthRequest.MenuAuth authRequest) {
        return sqlSession.selectOne(PRE_NS.concat("selectMenuAuthYnForOms"), authRequest);
    }


    public Integer selectCheckMenuCount(String menuUri) {
        return sqlSession.selectOne(PRE_NS.concat("selectCheckMenuCount"), menuUri);
    }

    /**
     * 메뉴 엑셀 동적 파라미터 가져오기
     *
     * @return
     */
    public MenuResponse.ExcelDynamicParam selectExcelDynamicParam() {
        return sqlSession.selectOne(PRE_NS.concat("selectExcelDynamicParam"));
    }

    /**
     * 메뉴 엑셀 리스트 조회
     *
     * @param menuRequest
     * @return
     */
    public List<HashMap<String, String>> selectMenuExcelList(MenuRequest.ExcelDynamicParam menuRequest) {
        return sqlSession.selectList(PRE_NS.concat("selectMenuExcelList"), menuRequest);
    }
}
