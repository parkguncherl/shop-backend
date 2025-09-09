package com.shop.core.biz.common.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Description: 그리드 Request
 */

@Getter
@Setter
@Schema(name = "GridRequest")
public class GridRequest {

    @JsonIgnore
    @Schema(description = "사용자ID")
    private Integer userId;

    @Schema(description = "페이지 URI")
    private String uri;

    @JsonProperty("columnState")
    @Schema(description = "컬럼정의 JSON")
    private String setValue;
}
