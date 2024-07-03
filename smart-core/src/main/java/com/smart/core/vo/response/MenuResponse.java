package com.smart.core.vo.response;

import com.smart.core.entity.Menu;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MenuResponse extends Menu {

    /** NO */
    private Integer no;

    /** 상위 코드 명 */
    private String menuUpperNm;

    /** 하위 코드 수 */
    private Integer lowerCodeCnt;
}
