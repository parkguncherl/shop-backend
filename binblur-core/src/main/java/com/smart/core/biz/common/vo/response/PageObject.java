package com.binblur.core.biz.common.vo.response;

import com.google.gson.GsonBuilder;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import com.binblur.core.entity.adapter.LocalTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * <pre>
 * Description : Paging 기본 Object
 * Date : 2023/02/04 01:35 PM
 * Company : smart90
 * Author : harry
 * </pre>
 */
@Setter
@Getter
public class PageObject {

    // 현재 페이지
    private Integer curPage;

    // 페이지당 ROW 건수
    private Integer pageRowCount;

    // 화면에 보여지는 페이지 블록 수
    private int pageBlockSize;

    // 페이지 블록의 시작 페이지번호
    private int pageBlockStartNo;

    // 페이지 블록의 종료 페이지번호
    private int pageBlockFinishNo;

    // 이전 페이지 블록의 시작 페이지번호
    private int pageBlockStartNo4Back;

    // 다음 페이지 블록의 시작 페이지번호
    private int pageBlockStartNo4Next;

    // 전체 데이터 건수
    private long totalRowCount;

    // 전체 페이지 수
    private int totalPageCount;

    // 화면에 보여지는 페이브 블록 배열
    private List<Integer> pageBlocks;

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
