package com.shop.core.interfaces;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <pre>
 * Description: 차트기본리스트
 * Date: 2024/10/21 12:35 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */

public class Series {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "SerieData", description = "차트기본리스트", type = "object")
    public static class SerieData<I> {
        /** 각각의 차트 적용 영역에서 사용할 시 상속을 통해 별도로 필요한 필드를 정의할 필요 */

        /**
         * 이름
         */
        @Schema(description = "name")
        private String name;

        /**
         * type
         */
        @Schema(description = "type")
        private String type;

        /**
         * emphasis
         */
        @Schema(description = "emphasis")
        private emphasis emphasis;

        /**
         * data(I)(차트 표시를 위한 기본 데이터)
         */
        @Setter
        @Schema(description = "data list")
        private List<I> Data;
    }

    /*@Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "SerieDataForNotInChart", description = "차트기본리스트 로서 사용되지 않는 데이터를 위한 요소(차트 인덱스에 대응하여 사용 가능한 배열을 반환하고자 하는 목적)", type = "object")
    public static class SerieDataForNotInChart<T> {

        @Schema(description = "name")
        private String name;

        @Schema(description = "data list")
        private List<T> data;
    }*/

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "emphasis", description = "emphasis", type = "object")
    public class emphasis {

        /**
         * emphasis
         */
        @Schema(description = "emphasis")
        private String focus = "series";
    }
}
