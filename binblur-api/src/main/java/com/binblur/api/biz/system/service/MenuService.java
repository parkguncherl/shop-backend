package com.binblur.api.biz.system.service;

import com.binblur.core.biz.common.vo.request.PageRequest;
import com.binblur.core.biz.common.vo.response.PageResponse;
import com.binblur.core.biz.system.dao.CodeDao;
import com.binblur.core.biz.system.dao.MenuDao;
import com.binblur.core.biz.system.vo.request.AuthRequest;
import com.binblur.core.biz.system.vo.request.MenuRequest;
import com.binblur.core.biz.system.vo.response.AuthResponse;
import com.binblur.core.biz.system.vo.response.CodeResponse;
import com.binblur.core.biz.system.vo.response.MenuResponse;
import com.binblur.core.entity.Auth;
import com.binblur.core.entity.Menu;
import com.binblur.core.entity.User;
import com.binblur.core.enums.BooleanValueCode;
import com.binblur.core.enums.ExcelTitleCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * Description : 메뉴_관리 Service
 * Date : 2023/02/06 11:57 AM
 * Company : smart90
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Service
@Transactional(transactionManager = "dataTxManager")
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CodeDao codeDao;

    /**
     * 메뉴_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PageResponse<MenuResponse.Paging> selectMenuListPaging(PageRequest<MenuRequest.PagingFilter> pageRequest) {
        return menuDao.selectMenuListPaging(pageRequest);
    }

    /**
     * 메뉴_리스트_조회 (by menuCd)
     *
     * @param menu
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Menu> selectMenuList(Menu menu) {
        return menuDao.selectMenuList(menu);
    }

    /**
     * 메뉴별_권한_조회 (by menuCd)
     *
     * @param menuCd
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<AuthResponse.Entity> selectMenuAuthList(String menuCd) {
        return menuDao.selectMenuAuthList(menuCd);
    }

    /**
     * 메뉴_등록
     *
     * @param createMenuRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer insertMenu(MenuRequest.Create createMenuRequest) {

        Integer tranCnt = 0;
        tranCnt += menuDao.insertMenu(createMenuRequest);

        if (createMenuRequest.getMenuCd().length() == 4) {
            List<CodeResponse.LowerSelect> codeList = codeDao.selectLowerCodeByCodeUpper("S0002");

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
                    auth.setCreateUser(createMenuRequest.getCreateUser());
                    auth.setUpdateUser(createMenuRequest.getCreateUser());
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
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
            auth.setCreateUser(user.getLoginId());
            auth.setUpdateUser(user.getLoginId());
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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Menu selectMenuById(Integer menuId) {
        return menuDao.selectMenuById(menuId);
    }

    /**
     * 메뉴_조회 (by Id)
     *
     * @param menu
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
     * 메뉴_삭제
     *
     * @param deleteMenuRequest
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
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
                delete.setUpdateUser(deleteMenuRequest.getUpdateUser());
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
    @Transactional(propagation = Propagation.REQUIRED)
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
            menu.setCreateUser(user.getLoginId());
            menu.setUpdateUser(user.getLoginId());
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
                    auth.setCreateUser(user.getLoginId());
                    auth.setUpdateUser(user.getLoginId());
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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AuthResponse.MenuAuth selectMenuAuthYn(Integer userId, String authCd, String menuUri) {
        AuthRequest.MenuAuth authRequest = new AuthRequest.MenuAuth();
        authRequest.setUserId(userId);
        authRequest.setAuthCd(authCd);
        authRequest.setMenuUri(menuUri);

        // 계정별 메뉴 권한 플래그 조회
        AuthResponse.MenuAuth menuAuth = menuDao.selectMenuAuthYn(authRequest);

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
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public BooleanValueCode selectCheckMenuCount(String menuUri) {
        Integer count = menuDao.selectCheckMenuCount(menuUri);

        if (count == null || count.intValue() == 0) {
            return BooleanValueCode.N;
        }

        return BooleanValueCode.Y;
    }

}
