package com.binblur.core.biz.system.vo.response;

import com.binblur.core.entity.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class ClientResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "ClientResponsePaging", description = "거래처 페이징 응답", type = "object")
    public static class Paging extends Client {}
}
