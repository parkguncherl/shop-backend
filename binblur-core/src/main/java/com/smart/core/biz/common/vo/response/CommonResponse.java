package com.binblur.core.biz.common.vo.response;

import com.binblur.core.entity.FileMng;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description : 공통 Response
 * Date : 2023/04/24 11:35 AM
 * Company : smart90
 * Author : seungcheollee
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CommonResponse {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CommonResponseSelectFile", description = "파일 응답", type = "object")
    public static class SelectFile extends FileMng {

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CommonResponseSelectFiles", description = "파일 목록 응답", type = "object")
    public static class SelectFiles extends FileMng {

        @Schema(description = "등록자_명")
        @Parameter(description = "등록자_명")
        private String createUserNm;
    }
}
