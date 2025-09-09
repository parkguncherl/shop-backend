package com.shop.core.biz.system.vo.request;

import com.shop.core.entity.Company;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

/**
 * <pre>
 * Description: 속성 Request
 * Company: home
 * Author : park
 * </pre>
 */

@Schema(name = "CompanyRequest")
public class CompanyRequest {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "CompanyRequestPagingFilter", description = "Company 페이징 필터")
    @ParameterObject
    public static class PagingFilter extends Company implements RequestFilter {}

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "CompanyRequestUpdate", description = "Company 수정")
    public static class Update extends Company {}

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "CompanyRequestUpdate", description = "Company 등록")
    public static class Insert extends Company {}

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "CompanyRequestDelete", description = "Company 삭제")
    public static class Delete extends Company {}
}
