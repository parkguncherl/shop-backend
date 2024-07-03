package com.binblur.core.biz.system.vo.response;

import com.binblur.core.entity.Company;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class CompanyResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "CompanyResponsePaging", description = "회사 페이징 응답", type = "object")
    public static class Paging extends Company {

        @Schema(description = "전체 등록 사용자 수")
        @Parameter(description = "전체 등록 사용자 수")
        protected Long registeredUserCount;
    }
}
