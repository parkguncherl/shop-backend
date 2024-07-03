package com.binblur.core.vo.request;

import com.binblur.core.entity.Code;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description : 코드 Request
 * Date : 2023/02/20 11:58 AM
 * Company : smart
 * Author : sclee9946
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CodeRequest extends Code {

    /** NO */
    private Integer no;

    /** 상위 코드 명 */
    private String codeUpperNm;

    /** 하위 코드 수 */
    private Integer lowerCodeCnt;
}
