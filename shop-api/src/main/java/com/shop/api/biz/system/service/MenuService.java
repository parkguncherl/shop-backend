package com.shop.api.biz.system.service;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.dao.CodeDao;
import com.shop.core.biz.system.dao.MenuDao;
import com.shop.core.biz.system.vo.request.AuthRequest;
import com.shop.core.biz.system.vo.request.MenuRequest;
import com.shop.core.biz.system.vo.response.AuthResponse;
import com.shop.core.biz.system.vo.response.CodeResponse;
import com.shop.core.biz.system.vo.response.MenuResponse;
import com.shop.core.entity.Auth;
import com.shop.core.entity.Menu;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.enums.BooleanValueCode;
import com.shop.core.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * Description: 메뉴_관리 Service
 * Date: 2023/02/06 11:57 AM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuDao menuDao;
    private final CodeDao codeDao;
    private final UserService userService;

    /**
     * 메뉴_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<MenuResponse.Paging> selectMenuListPaging(PageRequest<MenuRequest.PagingFilter> pageRequest) {
        return menuDao.selectMenuListPaging(pageRequest);
    }

    /**
     * 메뉴_리스트_조회 (by menuCd)
     *
     * @param menu
     * @return
     */
    public List<Menu> selectMenuList(Menu menu) {
        return menuDao.selectMenuList(menu);
    }


    /**
     * 메뉴_리스트_조회 (by user)
     *
     * @param menu
     * @return
     */
    public List<Menu> selectMenuListForUser(Menu menu) {
        return menuDao.selectMenuListForUser(menu);
    }

    /**
     * (인가 여부를 포함한)메뉴_리스트_조회 (by user)
     *
     * @param menu
     * @return
     */
    public List<MenuResponse.WithAuthSub> selectMenuListWithAuth(Menu menu) {
        return menuDao.selectMenuListWithAuth(menu);
    }

    /**
     * (메뉴 목록)권한의 인가 여부 변경 (by 사용자별)
     *
     * @param withAuth
     * @return
     */
    @Transactional
    public void updateUserMenuAuth(MenuRequest.WithAuth withAuth, User jwtUser) {
        User user = userService.selectUserById(jwtUser.getId());
        for (MenuRequest.WithAuthElement withAuthElement: withAuth.getWithAuthList()) {
            // top menu 는 tb_user_menu 에 추가 혹은 수정하지 않는다
            for (MenuRequest.WithAuthSubElement withAuthSubElement : withAuthElement.getItems()) {
                // 하위 메뉴 순회
                // 상위 메뉴 코드는 요청 시 포함되었다고 전제함
                /** tb_user_menu 영역에 사용자 권한에 관한 정보가 존재하는지 확인 */
                withAuthSubElement.setUserId(withAuth.getUserId()); // 사용자 id는 배열 요소 각각에 저장된 값에 의존하지 않는다(withAuths 인자에 포함된 값을 이용)
                if (withAuthSubElement.getAuthYn() == null) {
                    withAuthSubElement.setAuthYn("N");
                }
                Menu selectedMenu = menuDao.selectUserMenu(withAuthSubElement);
                if (selectedMenu == null) {
                    // 메뉴 부재 시 tb_menu 에 insert
                    Integer insertedMenu = menuDao.insertUserMenu(withAuthSubElement);
                    if (insertedMenu == null) {
                        throw new CustomRuntimeException(ApiResultCode.FAIL,withAuthSubElement.getMenuNm() + " 권한을 추가하던 중 문제 발생");
                    }
                } else {
                    // 메뉴 존재할 시 업데이트
                    withAuthSubElement.setUpdUser(user.getLoginId());
                    Integer updated = menuDao.updateUserMenuAuth(withAuthSubElement);
                    if (updated == null) {
                        throw new CustomRuntimeException(ApiResultCode.FAIL, withAuthSubElement.getMenuNm() + " 권한을 업데이트 하던 중 문제 발생");
                    }
                }
            }
        }
    }

    /**
     * 메뉴별_권한_조회 (by menuCd)
     *
     * @param menuCd
     * @return
     */
    public List<AuthResponse.Entity> selectMenuAuthList(String menuCd) {
        return menuDao.selectMenuAuthList(menuCd);
    }

    /**
     * 메뉴_등록
     *
     * @param createMenuRequest
     * @return
     */
    public Integer insertMenu(MenuRequest.Create createMenuRequest) {

        Integer tranCnt = 0;
        tranCnt += menuDao.insertMenu(createMenuRequest);

        if (createMenuRequest.getMenuCd().length() == 4) {
            List<CodeResponse.LowerSelect> codeList = codeDao.selectLowerCodeByCodeUpper("10020");

            if (!codeList.isEmpty()) {
                for (CodeResponse.LowerSelect codeResponse : codeList) {
                    Auth auth = new Auth();
                    auth.setAuthCd(codeResponse.getCodeCd());
                    auth.setMenuCd(createMenuRequest.getMenuCd());
                    if (StringUtils.equals(codeResponse.getCodeCd(), "90")) {
                        auth.setMenuReadYn("Y");
                        auth.setMenuUpdYn("Y");
                        auth.setMenuExcelYn("Y");
                    } else {
                        auth.setMenuReadYn("N");
                        auth.setMenuUpdYn("N");
                        auth.setMenuExcelYn("N");
                    }
                    auth.setCreUser(createMenuRequest.getCreUser());
                    auth.setUpdUser(createMenuRequest.getCreUser());
                    menuDao.insertMenuAuth(auth);
                }
            }
        }

        return tranCnt;
    }

    /**
     * 메뉴_수정
     *
     * @param updateMenuRequest
     * @return
     */
    public Integer updateMenu(MenuRequest.Update updateMenuRequest) {
        return menuDao.updateMenu(updateMenuRequest);
    }

    /**
     * 메뉴_권한_등록
     *
     * @param authRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer insertMenuAuth(AuthRequest.Create authRequest, User user) {

        Integer tranCount = 0;
        for (Auth auth : authRequest.getAuthList()) {
            auth.setMenuCd(authRequest.getMenuCd());
            Auth dao = menuDao.selectMenuAuthByUk(auth);
            auth.setCreUser(user.getLoginId());
            auth.setUpdUser(user.getLoginId());
            if (dao == null) {
                tranCount += menuDao.insertMenuAuth(auth);
            } else {
                if (menuDao.selectMenuAuth(auth) == null) {
                    tranCount += menuDao.updateMenuAuth(auth);
                }
            }
        }
        return tranCount;
    }

    /**
     * 메뉴_조회 (by Id)
     *
     * @param menuId
     * @return
     */
    public Menu selectMenuById(Integer menuId) {
        return menuDao.selectMenuById(menuId);
    }

    /**
     * 메뉴_조회 (by Id)
     *
     * @param menu
     * @return
     */
    public Menu selectMenuByUK(Menu menu) {
        return menuDao.selectMenuByUK(menu);
    }

    /**
     * 메뉴건수 조회 밸리데이션 체크위한
     *
     * @param menu
     * @return
     */
    public Integer countMenuByNm(Menu menu) {
        return menuDao.countMenuByNm(menu);
    }

    /**
     * 메뉴건수 조회 밸리데이션 체크위한
     *
     * @param userId
     * @return
     */
    public String selectCustomRoles(Integer userId) {
        return menuDao.selectCustomRoles(userId);
    }


    /**
     * 메뉴_삭제
     *
     * @param deleteMenuRequest
     * @return
     */
    public Integer deleteMenu(MenuRequest.Delete deleteMenuRequest) {

        // 상위메뉴가 TOP인 경우 하위메뉴를 먼저 지움
        if (deleteMenuRequest.getUpMenuCd().equals(deleteMenuRequest.getMenuCd())) {
            Menu upMenu = Menu.builder()
                .upMenuCd(deleteMenuRequest.getUpMenuCd())
                .deleteYn(BooleanValueCode.N)
                .build();
            List<Menu> menus = menuDao.selectMenuListByUpMenuCd(upMenu);
            menus.forEach(menu -> {
                MenuRequest.Delete delete = new MenuRequest.Delete();
                delete.setId(menu.getId());
                delete.setUpdUser(deleteMenuRequest.getUpdUser());
                menuDao.deleteMenu(delete);
                menuDao.deleteMenuAuth(menu.getMenuCd());
            });
        }

        Integer tranCount = menuDao.deleteMenu(deleteMenuRequest);

        // 메뉴를 삭제하면 메뉴의 권한도 delete
        if (tranCount > 0) {
            tranCount += menuDao.deleteMenuAuth(deleteMenuRequest.getMenuCd());
        }

        return tranCount;
    }

    /**
     * 상위_메뉴_목록_조회
     *
     * @return
     */
    public List<Menu> selectTopMenuList() {
        return menuDao.selectTopMenuList();
    }

    /**
     * 메뉴_등록_일괄엑셀
     *
     * @param createMenuList
     * @return
     */
    public Integer insertMenuList(List<HashMap<String, String>> createMenuList, MenuResponse.ExcelDynamicParam menuResponse, User user) {
        menuDao.deleteMenuAll(); // 전체 삭제한다.
        menuDao.deleteMenuAuthAll(); // 전체 권한 삭제한다.
        Integer tranCnt = 0;

        String[] excelColumns = menuResponse.getExcelColumn().split("\\|");

        for (int i = 0; i < createMenuList.size(); i++) {
            MenuRequest.Create menu = new MenuRequest.Create();
            menu.setUpMenuCd(createMenuList.get(i).get("UP_MENU_CD"));
            menu.setMenuCd(createMenuList.get(i).get("MENU_CD"));
            menu.setMenuOrder(Integer.parseInt(createMenuList.get(i).get("MENU_ORDER")));
            menu.setMenuNm(createMenuList.get(i).get("MENU_NM"));
            menu.setMenuEngNm(createMenuList.get(i).get("MENU_ENG_NM"));
            menu.setMenuUri(createMenuList.get(i).get("MENU_URI"));
            menu.setCreUser(user.getLoginId());
            menu.setUpdUser(user.getLoginId());
            tranCnt += menuDao.insertMenu(menu);

            // 하위메뉴만 권한이 존재한다.
            if (menu.getMenuCd().length() == 4) {
                for (int j = 0; j < excelColumns.length; j++) {
                    Auth auth = new Auth();
                    auth.setAuthCd(excelColumns[j].substring(5, excelColumns[j].length()));
                    auth.setMenuCd(menu.getMenuCd());
                    auth.setMenuReadYn(createMenuList.get(i).get(excelColumns[j]).split("")[0]);
                    if (StringUtils.equals(createMenuList.get(i).get(excelColumns[j]).split("")[1], "Y")) {
                        auth.setMenuReadYn("Y");
                    }
                    auth.setMenuUpdYn(createMenuList.get(i).get(excelColumns[j]).split("")[1]);
                    if (StringUtils.equals(createMenuList.get(i).get(excelColumns[j]).split("")[2], "Y")) {
                        auth.setMenuReadYn("Y");
                    }
                    auth.setMenuExcelYn(createMenuList.get(i).get(excelColumns[j]).split("")[2]);
                    auth.setCreUser(user.getLoginId());
                    auth.setUpdUser(user.getLoginId());
                    menuDao.insertMenuAuth(auth);
                }
            }
        }
        return tranCnt;
    }

    /**
     * 계정별 메뉴 권한 체크
     *
     * @param authCd
     * @param menuUri
     * @return
     */
    public BooleanValueCode selectAuthMenuCount(String authCd, String menuUri) {
        Integer count = menuDao.selectAuthMenuCount(authCd, menuUri);

        if (count == null || count.intValue() == 0) {
            return BooleanValueCode.N;
        }

        return BooleanValueCode.Y;
    }

    /**
     * 계정별 메뉴 권한 플래그 조회
     *
     * @param userId
     * @param authCd
     * @param menuUri
     * @return
     */
    public AuthResponse.MenuAuth selectMenuAuthYn(Integer userId, String authCd, String menuUri) {
        AuthRequest.MenuAuth authRequest = new AuthRequest.MenuAuth();
        authRequest.setUserId(userId);
        authRequest.setAuthCd(authCd);
        authRequest.setMenuUri(menuUri);

        AuthResponse.MenuAuth menuAuth;
        // 계정별 메뉴 권한 플래그 조회
        if(Integer.parseInt(authCd) < 400){
            menuAuth = menuDao.selectMenuAuthYnForOms(authRequest);
        } else {
            menuAuth = menuDao.selectMenuAuthYn(authRequest);
        }

        if (menuAuth == null) {
            menuAuth.setMenuReadYn("N");
            menuAuth.setMenuUpdYn("N");
            menuAuth.setMenuExcelYn("N");
            return menuAuth;
        }

        return menuAuth;
    }

    /**
     * 계정별 메뉴 권한 체크 대상확인
     *
     * @param menuUri
     * @return
     */
    public BooleanValueCode selectCheckMenuCount(String menuUri) {
        Integer count = menuDao.selectCheckMenuCount(menuUri);

        if (count == null || count.intValue() == 0) {
            return BooleanValueCode.N;
        }

        return BooleanValueCode.Y;
    }

}
