package com.shop.core.vo.response;

import com.shop.core.entity.Code;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description: 코드 Response
 * Date: 2023/02/16 14:58 PM
 * Company: smart
 * Author : luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CodeResponse extends Code {

    /** NO */
    private Integer no;

    /** 상위 코드 명 */
    private String codeUpperNm;

    /** 하위 코드 수 */
    private Integer lowerCodeCnt;
}
