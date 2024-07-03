package com.smart.core.biz.system.vo.request;

import com.smart.core.entity.Attribute;
import com.smart.core.entity.Client;
import com.smart.core.entity.Order;
import com.smart.core.interfaces.RequestFilter;
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
