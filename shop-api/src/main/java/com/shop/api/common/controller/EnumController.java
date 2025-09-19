package com.shop.api.common.controller;

import com.shop.core.biz.common.vo.response.EnumResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.core.interfaces.CodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Description: ENUM Controller
 * Date: 2024/06/18 11:10 PM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/enum")
@Tag(name = "EnumController", description = "ENUM 관련 API")
public class EnumController {

    /**
     * ENUM 정보 반환
     * @param enumName
     * @return
     */
    @GetMapping(value = "/{enumName}")
    @Operation(summary = "ENUM 항목 조회")
    public ApiResponse<List<EnumResponse>> getEnumInfo(@PathVariable("enumName") String enumName) {

        try {
            Class enumObj = Class.forName("com.shop.core.enums." + enumName);

            if (enumObj.isEnum()) {
                List<EnumResponse> responseEnumList = new ArrayList<>();
                for (Field field : enumObj.getDeclaredFields()) {
                    if (field.isEnumConstant()) {
                        CodeEnum codeEnum = (CodeEnum) field.get(field);
                        responseEnumList.add(EnumResponse
                                .builder()
                                .name(codeEnum.toString())
                                .code(codeEnum.getCode())
                                .message(codeEnum.getMessage())
                                .build()
                        );
                    }
                }

                return new ApiResponse<>(ApiResultCode.SUCCESS, responseEnumList);
            } else {
                throw new CustomRuntimeException(ApiResultCode.BAD_REQUEST);
            }
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new CustomRuntimeException(ApiResultCode.DATA_NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomRuntimeException(ApiResultCode.BAD_REQUEST);
        }
    }
}
