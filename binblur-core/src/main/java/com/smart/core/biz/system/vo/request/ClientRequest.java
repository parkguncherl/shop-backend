package com.binblur.core.biz.system.vo.request;

import com.binblur.core.entity.Attribute;
import com.binblur.core.entity.Client;
import com.binblur.core.entity.Order;
import com.binblur.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

/**
 * <pre>
 * Description : 거래처 Request
 * Company : home
 * Author : park
 * </pre>
 */

@Schema(name = "ClientRequest")
public class ClientRequest {

    public static Object Update;

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "ClientRequestPagingFilter", description = "Client 페이징 필터")
    @ParameterObject
    public static class PagingFilter extends Client implements RequestFilter {}
}
