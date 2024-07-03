package com.smart.core.vo.request;

import com.smart.core.entity.Menu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description : 메뉴 Request
 * Date : 2023/02/20 11:58 AM
 * Company : smart
 * Author : sclee9946
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MenuRequest extends Menu {

    /** 전체권한 */
    private String auths;
}

