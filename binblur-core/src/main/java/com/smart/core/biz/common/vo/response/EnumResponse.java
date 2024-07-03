package com.binblur.core.biz.common.vo.response;

import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description : ENUM 정보 Response
 * Date : 2023/02/04 01:35 PM
 * Company : smart90
 * Author : kdonghwa
 * </pre>
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
public class EnumResponse {

    private String name;

    private String code;

    private String message;

    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}
