package com.shop.api.biz.system.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.system.service.MenuService;
import com.shop.api.biz.system.service.UserService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.AuthRequest;
import com.shop.core.biz.system.vo.request.MenuRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.AuthResponse;
import com.shop.core.biz.system.vo.response.MenuResponse;
import com.shop.core.entity.LeftMenu;
import com.shop.core.entity.LeftMenuSub;
import com.shop.core.entity.Menu;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.enums.EsseType;
import com.shop.core.enums.GlobalConst;
import com.shop.core.utils.CommUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Description: 메뉴접근 권한관리 Controller
 * Date :
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
@Tag(name = "MenuController", description = "메뉴접근 권한관리 관련 API")
public class MenuController {

    private final MenuService menuService;

    private final UserService userService;

    /**
     * 메뉴접근_권한관리_목록_조회 (페이징)
     *
     * @param jwtUser
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("메뉴접근 권한관리 목록 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "메뉴접근 권한관리 목록 조회 (페이징)")
    public ApiResponse<PageResponse<MenuResponse.Paging>> selectMenuListPaging(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "MenuRequestPagingFilter", description = "메뉴접근 권한관리 목록 조회 (페이징) 필터", in = ParameterIn.PATH) MenuRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "메뉴접근 권한관리 목록 조회 페이징") PageRequest<MenuRequest.PagingFilter> pageRequest
    ) {

        if (StringUtils.isEmpty(filter.getUpMenuCd())) {
            filter.setUpMenuCd("TOP");
        }
        pageRequest.setFilter(filter);

        PageResponse<MenuResponse.Paging> response = menuService.selectMenuListPaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 상위_메뉴_목록_조회
     *
     * @return
     */
    @GetMapping(value = "/top")
    @Operation(summary = "상위 메뉴 목록 조회")
    public ApiResponse<List<Menu>> selectTopMenuList() {

        List<Menu> menuList = menuService.selectTopMenuList();

        if (menuList.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_MENU);
        }

        return new ApiResponse<>(menuList);
    }

    /**
     * 메뉴별_권한_조회 (by menuCd)
     *
     * @param jwtUser
     * @param menuCd
     * @return
     */
    @GetMapping(value = "/auth/{menuCd}")
    @Operation(summary = "메뉴별 권한 조회")
    public ApiResponse<List<AuthResponse.Entity>> selectMenuAuthList(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "메뉴 코드") @PathVariable String menuCd
    ) {

        List<AuthResponse.Entity> menuList = menuService.selectMenuAuthList(menuCd);

        if (menuList.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_MENU);
        }

        return new ApiResponse<>(menuList);
    }

    /**
     * 메뉴_등록
     *
     * @param jwtUser
     * @param menuRequest
     * @return
     */
    @AccessLog("메뉴 등록")
    @PostMapping("")
    @Operation(summary = "메뉴 등록")
    public ApiResponse insertMenu(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "메뉴 등록 Request") @RequestBody MenuRequest.Create menuRequest
    ) {
        Integer saveCount = 0;
        menuRequest.setCreUser(jwtUser.getLoginId());
        menuRequest.setUpdUser(jwtUser.getLoginId());
        // 상위메뉴 추가 인경우
        if (StringUtils.equals("TOP", menuRequest.getUpMenuCd())) {
            menuRequest.setUpMenuCd(menuRequest.getMenuCd());
        } else { // 하위메뉴 추가인경우 uri 검사한다.
            CommUtil.korFieldCheck(EsseType.ESS, menuRequest.getMenuUri(), 100, "메뉴URI");
        }

        ApiResponse apiResponse = this.checkMenu(menuRequest.getId(), menuRequest.getUpMenuCd(), menuRequest.getMenuCd(), menuRequest.getMenuNm(), "INS", menuRequest.getMenuUri());
        if (apiResponse != null) {
            return apiResponse;
        }

        saveCount = menuService.insertMenu(menuRequest);

        if (saveCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_CREATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 메뉴_수정
     *
     * @param jwtUser
     * @param menuRequest
     * @return
     */
    @AccessLog("메뉴 수정")
    @PutMapping("")
    @Operation(summary = "메뉴 수정")
    public ApiResponse updateMenu(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "메뉴 수정 Request") @RequestBody MenuRequest.Update menuRequest
    ) {
        Integer saveCount = 0;
        menuRequest.setUpdUser(jwtUser.getLoginId());
        // 상위메뉴 추가 인경우
        if (StringUtils.equals(menuRequest.getMenuCd(), menuRequest.getUpMenuCd())) {
            menuRequest.setUpMenuCd(menuRequest.getUpMenuCd());
        } else { // 하위메뉴 추가인경우 uri 검사한다.
            CommUtil.korFieldCheck(EsseType.ESS, menuRequest.getMenuUri(), 100, "메뉴URI");
        }

        ApiResponse apiResponse = this.checkMenu(menuRequest.getId(), menuRequest.getUpMenuCd(), menuRequest.getMenuCd(), menuRequest.getMenuNm(), "MOD", menuRequest.getMenuUri());

        if (apiResponse != null) {
            return apiResponse;
        }

        saveCount = menuService.updateMenu(menuRequest);
        if (saveCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }

        log.debug("=====> 메뉴 수정 성공");
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 메뉴_삭제
     *
     * @param jwtUser
     * @param menuRequest
     * @return
     */
    @AccessLog("메뉴 삭제")
    @DeleteMapping(value = "")
    @Operation(summary = "메뉴 삭제")
    public ApiResponse deleteMenu(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "메뉴 삭제 Request") @RequestBody MenuRequest.Delete menuRequest
    ) {

        // 세션정보를 이용해서 수정자 세팅
        menuRequest.setUpdUser(jwtUser.getLoginId());
        Integer deleteCount = menuService.deleteMenu(menuRequest);
        if (deleteCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_DELETE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 메뉴_권한_등록
     *
     * @param jwtUser
     * @param authRequest
     * @return
     */
    @AccessLog("메뉴 권한 등록")
    @PostMapping("/auth/reg")
    @Operation(summary = "메뉴 권한 등록")
    public ApiResponse insertMenuAuth(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "메뉴 권한 등록 Request") @RequestBody AuthRequest.Create authRequest
    ) {

        log.debug(">>>>>> auth = {}", authRequest);

        Integer tranCount = menuService.insertMenuAuth(authRequest, jwtUser);

        if (tranCount == 0) {
            return new ApiResponse<>(ApiResultCode.NOT_CHANGE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 권한_있는_메뉴_목록_조회
     *
     * @param jwtUser
     * @return
     */
    @GetMapping(value = "/leftMenu")
    @Operation(summary = "권한 있는 메뉴 목록 조회")
    public ApiResponse<List<LeftMenu>> selectLeftMenu(@Parameter(hidden = true) @JwtUser User jwtUser) {
        MenuRequest.Create sMenu = new MenuRequest.Create();
        User selUser = userService.selectUserById(jwtUser.getId());
        sMenu.setAuthCd(selUser.getAuthCd());
        sMenu.setUpMenuCd(GlobalConst.TOP_MENU.getCode());
        sMenu.setUserId(jwtUser.getId());
        List<LeftMenu> leftMenus = new ArrayList<>();
        boolean isOmsUser = Integer.parseInt(selUser.getAuthCd()) < 400; // oms 사용자인경우
        for (Menu menu : isOmsUser ? menuService.selectMenuListForUser(sMenu) : menuService.selectMenuList(sMenu)) {
            LeftMenu leftMenu = new LeftMenu();
            leftMenu.setMenuNm(menu.getMenuNm());
            leftMenu.setMenuCd(menu.getMenuCd());
            leftMenu.setIconClassName(menu.getMenuUri());
            sMenu.setUpMenuCd(menu.getMenuCd());
            List<LeftMenuSub> leftMenuSubs = new ArrayList<>();
            boolean isFirst = true;
            for (Menu sub : isOmsUser ? menuService.selectMenuListForUser(sMenu) : menuService.selectMenuList(sMenu)) {
                LeftMenuSub leftMenuSub = new LeftMenuSub();
                leftMenuSub.setMenuNm(sub.getMenuNm());
                if (isFirst && StringUtils.isNotEmpty(sub.getMenuUri()) && (sub.getMenuUri().length() > 1)) {
                    String uri = sub.getMenuUri().substring(1);
                    String uriArray[] = uri.split("\\/");
                    leftMenu.setMenuUri(uriArray[0]);
                    isFirst = false;
                }
                leftMenuSub.setMenuCd(sub.getMenuCd());
                leftMenuSub.setMenuUri(StringUtils.defaultIfEmpty(sub.getMenuUri(), "/"));
                leftMenuSubs.add(leftMenuSub);
            }
            leftMenu.setItems(leftMenuSubs);
            leftMenus.add(leftMenu);
        }
        if (leftMenus.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_MENU);
        }

        return new ApiResponse<>(leftMenus);
    }

    /**
     * 특정 사용자에게 설정 가능한 메뉴 목록을 인가 여부와 함께 조회
     *
     * @param jwtUser
     * @return
     */
    @GetMapping(value = "/withAuth/{userId}")
    @Operation(summary = "특정 사용자에게 설정 가능한 메뉴 목록을 인가 여부와 함께 조회")
    public ApiResponse<List<MenuResponse.WithAuth>> selectMenuWithAuth(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "메뉴 코드") @PathVariable Integer userId
    ) {
        MenuRequest.Create sMenu = new MenuRequest.Create();
        sMenu.setUpMenuCd(GlobalConst.TOP_MENU.getCode());
        sMenu.setUserId(userId);
        List<MenuResponse.WithAuth> withAuths = new ArrayList<>();
        List<MenuResponse.WithAuthSub> withAuthSubList = menuService.selectMenuListWithAuth(sMenu);
        for (MenuResponse.WithAuthSub sub : withAuthSubList) {
            MenuResponse.WithAuth withAuth = new MenuResponse.WithAuth();
            withAuth.setMenuNm(sub.getMenuNm());
            withAuth.setMenuCd(sub.getMenuCd());
            sMenu.setUpMenuCd(sub.getMenuCd());
            List<MenuResponse.WithAuthSub> MenuItems = new ArrayList<>(menuService.selectMenuListWithAuth(sMenu));
            for(MenuResponse.WithAuthSub subMenuItem : MenuItems) {
                if(!StringUtils.equals("Y", subMenuItem.getAuthYn())){
                    sub.setAuthYn("N");
                    break;
                }
            }
            withAuth.setItems(MenuItems); // 각각의 menu 반환값에 upMenuCd 조건에 따라 분기된 쿼리문에 의하여 반환된 하위 메뉴 아이템 List 를 추가함
            withAuth.setAuthYn(sub.getAuthYn());
            withAuths.add(withAuth);
        }
        if (withAuths.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_MENU);
        }

        return new ApiResponse<>(withAuths);
    }

    /**
     * 사용자별 (메뉴 목록)권한의 인가 여부 변경
     *
     * @param withAuth
     * @param jwtUser
     * @return
     */
    @PatchMapping(value = "/userMenuAuth")
    @Operation(summary = "사용자별 (메뉴 목록)권한의 인가 여부 변경")
    public ApiResponse updateUserMenuAuth(
            @RequestBody MenuRequest.WithAuth withAuth,
            @JwtUser User jwtUser
    ) {
        /** 내부적으로 예외가 발생하지 않을 경우 성공으로 간주 */
        menuService.updateUserMenuAuth(withAuth, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 메뉴 수정 및 입력 validation
     *
     * @param id
     * @param upMenuCd
     * @param menuCd
     * @param menuNm
     * @param modType
     * @param meunUri
     * @return
     */
    private ApiResponse checkMenu(Integer id, String upMenuCd, String menuCd, String menuNm, String modType, String meunUri) {

        if (upMenuCd.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            return new ApiResponse<>(ApiResultCode.MENU_CODE_CHECK);
        }

        upMenuCd = upMenuCd.trim();

        // 대메뉴는 대문자
        if (!CommUtil.isStringUpperCase(upMenuCd) || upMenuCd.length() != 2) {
            return new ApiResponse<>(ApiResultCode.BIG_MENU_CHECK);
        }

        // 하위메뉴도 대문자
        if (!CommUtil.isStringUpperCase(menuCd)) {
            return new ApiResponse<>(ApiResultCode.BIG_MENU_CHECK);
        }

        // 상위메뉴 입력
        if (!StringUtils.equals(upMenuCd, menuCd)) {
            if (menuCd.length() != 4) {
                return new ApiResponse<>(ApiResultCode.MENU_CAP_CHECK);
            }
        }

        // 대메뉴 말고 하위메뉴만
        if (StringUtils.isNotEmpty(meunUri) && menuCd.length() == 4) {
            Menu menu2 = new Menu();
            menu2.setMenuUri(meunUri);
            menu2.setId(id);
            if (menuService.selectMenuByUK(menu2) != null) {
                return new ApiResponse<>(ApiResultCode.DUPLICATE_URI);
            }
        }

        if (StringUtils.equals("INS", modType)) {
            Menu menu = new Menu();
            menu.setUpMenuCd(upMenuCd);
            menu.setMenuCd(menuCd);
            if (menuService.selectMenuByUK(menu) != null) {
                return new ApiResponse<>(ApiResultCode.DUPLICATE_MENU);
            }

            menu.setUpMenuCd(null);
            menu.setMenuCd(null);
            menu.setMenuNm(menuNm);
            if (menuService.selectMenuByUK(menu) != null) {
                return new ApiResponse<>(ApiResultCode.DUPLICATE_MENU_NM);
            }
        } else {
            Menu menu = new Menu();
            menu.setUpMenuCd(upMenuCd);
            menu.setMenuCd(menuCd);
            menu.setId(id);

            if(id == null || id < 1){
                return new ApiResponse<>(ApiResultCode.FAIL, "아이디가 존재하지 않습니다.");
            }

            menu.setMenuNm(menuNm);

            if (menuService.countMenuByNm(menu) > 0) {
                return new ApiResponse<>(ApiResultCode.DUPLICATE_MENU);
            }
        }
        return null;
    }
}
