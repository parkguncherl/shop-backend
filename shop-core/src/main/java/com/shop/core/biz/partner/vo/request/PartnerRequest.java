package com.shop.core.biz.partner.vo.request;

import com.shop.core.entity.Partner;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * <pre>
 * Description: 화주 Request
 * Date: 2024/07/26 03:00 PM
 * Company: smart90
 * Author: hyunDelay
 * </pre>
 */
@Schema(name = "PartnerRequest")
public class PartnerRequest {
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerRequestPagingFilter", description = "화주계정 페이징 필터")
    @ParameterObject
    public static class PagingFilter implements RequestFilter {
        @Schema(description = "아이디")
        @Parameter(description = "아이디")
        private String id;

        @Schema(description = "회사명")
        @Parameter(description = "회사명")
        private String partnerNm;

        @Schema(description = "검색 시작일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate startDate;

        @Schema(description = "검색 종료일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate endDate;

        @Schema(description = "파트너_구분")
        @Parameter(description = "파트너_구분")
        private String upperPartnerId;

        @Schema(description = "파트너_아이디")
        @Parameter(description = "파트너_아이디")
        private Integer partnerId;

    }

    @Getter
    @Setter
    @Schema(name = "PartnerRequestFilterForList", description = "화주계정 리스트 검색 필터")
    public static class FilterForList implements RequestFilter {

        @Schema(description = "회사명")
        private String partnerNm;
    }

    // 공통 Base: Partner 엔티티를 상속받아 모든 필드를 그대로 사용
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public abstract static class Base extends Partner implements RequestFilter {

        /** Base 자체가 Partner이므로 this를 반환 */
        public Partner toEntity() {
            if (getUpdUser() == null) setUpdUser(getCreUser());
            return this;
        }
    }

    @Schema(name = "PartnerRequestCreate", description = "화주계정 생성 요청 파라미터")
    public static class Create extends Base {
    }

    @Schema(name = "PartnerRequestUpdate", description = "화주계정 수정 요청 파라미터")
    public static class Update extends Base {
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerRequestDelete", description = "화주계정 삭제 요청 파라미터")
    public static class Delete implements RequestFilter {

        @Schema(description = "아이디(PK)")
        private Integer id;
    }

}
