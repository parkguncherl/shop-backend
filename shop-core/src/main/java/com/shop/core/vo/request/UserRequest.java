package com.shop.core.vo.request;

import com.shop.core.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description: 사용자 Request
 * Date: 2023/02/27 16:58 PM
 * Company: smart
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserRequest extends User {

    /** NO */
    private Integer no;

    /** 권한_명 */
    private String authNm;
}
