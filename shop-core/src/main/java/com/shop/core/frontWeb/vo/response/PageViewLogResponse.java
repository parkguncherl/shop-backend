package com.shop.core.frontWeb.vo.response;

import com.shop.core.entity.PageViewLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class PageViewLogResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PageViewLogResponseInfo", description = "페이지 뷰 로그 정보", type = "object")
    public static class Info extends PageViewLog {

    }
}