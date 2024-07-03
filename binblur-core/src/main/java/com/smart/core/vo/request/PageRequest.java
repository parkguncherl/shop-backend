package com.binblur.core.vo.request;

import com.google.gson.GsonBuilder;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import com.binblur.core.enums.SortTypeCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * <pre>
 * Description : Paging 데이터 Request
 * Date : 2023/02/04 01:35 PM
 * Company : smart
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Getter
@Setter
public class PageRequest<T> {

    /** filter Entity */
    protected T filter;

    /** 페이지당 ROW 수 */
    protected Integer pageRowCount;

    /** 현재 페이지 */
    protected Integer curPage;

    /** 검색 시작일자(YYYY-MM-DD) */
    protected String startDate = LocalDate.now().minusMonths(1L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd") );

    /** 검색 종료일자(YYYY-MM-DD) */
    protected String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd") );

    /** 정렬 컬럼명 */
    protected String sortColumn;

    /** 정렬 방법 */
    protected SortTypeCode sortType;

    /** 파일 아이디 */
    protected Long fileGroupId;

    public Integer getStartIndex(){
        return pageRowCount * (curPage.intValue() -1);
    }

    public PageRequest() {
        this.curPage = 1;
        this.pageRowCount = 10;
        this.sortColumn = "id";
        this.sortType = SortTypeCode.DESC;
        this.filter = null;
    }

    public PageRequest(int curPage, int pageRowCount) {
        this.curPage = curPage;
        this.pageRowCount = pageRowCount;
        this.filter = null;
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }

}
