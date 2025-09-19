package com.shop.core.biz.common.vo.response;

import com.shop.core.entity.FileDet;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description: 공통 Response
 * Date: 2023/04/24 11:35 AM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CommonResponse {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "CommonResponseSelectFile", description = "파일 응답", type = "object")
    public static class SelectFile extends FileDet {

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "CommonResponseSelectFiles", description = "파일 목록 응답", type = "object")
    public static class SelectFiles extends FileDet {

        @Schema(description = "등록자_명")
        @Parameter(description = "등록자_명")
        private String createUserNm;
    }


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "CommonResponseFileDown", description = "파일 응답", type = "object")
    public static class FileDown extends FileDet {

        public FileDet toEntity() {
            return FileDet.builder()
                    .fileId(getFileId())
                    .creUser(getCreUser())
                    .creTm(getCreTm())
                    .updUser(getUpdUser())
                    .updTm(getUpdTm())
                    .deleteYn(getDeleteYn())
                    .build();
        }
    }

}
