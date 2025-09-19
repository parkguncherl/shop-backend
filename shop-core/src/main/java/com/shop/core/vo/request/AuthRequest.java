package com.shop.core.vo.request;

import com.shop.core.entity.Auth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * <pre>
 * Description: 메뉴 Request
 * Date: 2023/02/20 11:58 AM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AuthRequest {

    /** 메뉴 */
    private String menuCd;
    
    /** 권한목록 */
    private ArrayList<Auth> authList;

}
