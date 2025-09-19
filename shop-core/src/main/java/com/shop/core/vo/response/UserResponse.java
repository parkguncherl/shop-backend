package com.shop.core.vo.response;

import com.shop.core.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description: 사용자 Response
 * Date: 2023/02/27 16:57 PM
 * Company: smart
 * Author: luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserResponse extends User {

    /** NO */
    private Integer no;

    /** 권한_명 */
    private String authNm;
}
