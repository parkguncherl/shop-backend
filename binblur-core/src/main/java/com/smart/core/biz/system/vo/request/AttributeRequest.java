package com.binblur.core.biz.system.vo.request;

import com.binblur.core.entity.Attribute;
import com.binblur.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;

/**
 * <pre>
 * Description : 속성 Request
 * Company : home
 * Author : park
 * </pre>
 */

@Schema(name = "AttributeRequest")
public class AttributeRequest {

    public static Object Update;

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "AttributeRequestPagingFilter", description = "Attribute 페이징 필터")
    @ParameterObject
    public static class PagingFilter extends Attribute implements RequestFilter {}

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "AttributeRequestInsert", description = "Attribute 추가")
    public static class Insert extends Attribute {
        private String createUser;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "AttributeRequestUpdate", description = "Attribute 갱신")
    public static class Update extends Attribute {
        private String updateUser;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "AttributeRequestUpdate", description = "Attribute 최신화")
    public static class Modified {
        private List<Insert> inserted;
        private List<Update> updated;
    }
}
