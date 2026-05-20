package com.shop.core.frontWeb.vo.request;

import com.shop.core.entity.PageViewLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

public class PageViewLogRequest {


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @SuperBuilder
    @AllArgsConstructor
    @Schema(name = "PageViewLogRequestSave", description = "페이지 뷰 로그 저장 요청", type = "object")
    public static class Save extends PageViewLog {

    }
}
