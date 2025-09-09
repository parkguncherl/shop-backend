package com.shop.core.biz.common.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Description: 그리드 Response
 */

@Getter
@Setter
@Schema(name = "GridResponse")
public class GridResponse {

    @Schema(description = "페이지 URI")
    private String uri;

    @Schema(description = "컬럼정의 JSON")
    private String columnState;
}
