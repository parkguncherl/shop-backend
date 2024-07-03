package com.binblur.core.vo.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description : 메뉴 Request
 * Date : 2023/03/02 11:58 AM
 * Company : smart
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CommonRequest {

    /** 구성할 컬럼들 */
    private String columns;

    /** 엑셀 타이틀 */
    private String excelTitle;



}

