package com.shop.core.frontWeb.vo.response;

import com.shop.core.entity.Contents;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class ContentsResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ContentsResponseContentsInfo", description = "frontWeb 이하 컨텐츠 응답 dto", type = "object")
    public static class ContentsInfo extends Contents {
    }

}
