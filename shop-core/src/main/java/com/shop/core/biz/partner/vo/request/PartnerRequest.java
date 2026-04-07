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

    // 1. 공통 로직을 담은 추상 클래스 생성
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public abstract static class Base extends Partner implements RequestFilter {

        /** 본 인스턴스가 상속한 엔티티를 반환하는 공통 함수 */
        public Partner toEntity() {
            return Partner.builder()
                    // Update 시에는 id가 필요하므로 getId()를 포함하는 것이 좋습니다.
                    // (Create 시 id가 null이어도 DB의 Auto Increment가 동작하므로 무방함)
                    .id(getId())
                    .partnerNm(getPartnerNm())
                    .partnerTicker(getPartnerTicker())
                    .partnerSubNm(getPartnerSubNm())
                    .domain(getDomain())
                    .phoneNo(getPhoneNo())
                    .creUser(getCreUser())
                    .updUser(getUpdUser() != null ? getUpdUser() : getCreUser())
                    .build();
        }
    }

    // 2. Create 클래스 (Base 상속)
    @Schema(name = "PartnerRequestCreate", description = "화주계정 생성 요청 파라미터")
    public static class Create extends Base {
        // 추가로 Create에만 필요한 Validation(@NotBlank 등)이나 로직이 있다면 여기에 작성
    }

    // 3. Update 클래스 (Base 상속)
    @Schema(name = "PartnerRequestUpdate", description = "화주계정 수정 요청 파라미터")
    public static class Update extends Base {
        // 추가로 Update에만 필요한 로직이 있다면 여기에 작성
        // 예: @NotNull(message = "수정 시 ID는 필수입니다.")
        //    private Integer id;
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
