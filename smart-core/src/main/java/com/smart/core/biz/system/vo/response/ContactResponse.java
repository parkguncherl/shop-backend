package com.smart.core.biz.system.vo.response;

import com.smart.core.entity.Contact;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description : 접속로그 Response
 * Date : 2023/03/14 11:58 AM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ContactResponse  {

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "ContactResponsePaging", description = "접속로그 페이징 응답", type = "object")
    public static class Paging extends Contact {
        @Schema(required = false)
        @Parameter(description = "NO")
        private Integer no;

        @Schema(required = false)
        @Parameter(description = "거래구분명")
        private String tranTypeNm;

        @Schema(required = false)
        @Parameter(description = "로그인ID")
        private String loginId;

        @Schema(required = false)
        @Parameter(description = "회원_이름")
        private String userNm;

        @Schema(required = false)
        @Parameter(description = "소속_명")
        private String belongNm;

        @Schema(required = false)
        @Parameter(description = "부서_명")
        private String deptNm;

        @Schema(required = false)
        @Parameter(description = "권한_명")
        private String authNm;
    }

}
