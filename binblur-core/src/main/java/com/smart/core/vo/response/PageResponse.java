package com.binblur.core.vo.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.GsonBuilder;
import com.binblur.core.entity.adapter.LocalDateSerializer;
import com.binblur.core.entity.adapter.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Description : Paging 데이터 Response
 * Date : 2023/02/04 01:35 PM
 * Company : smart
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Setter
@Getter
public class PageResponse<T> {

    // 데이터 목록
    private List<T> rows;

    // 데이터 총합 목록
    private T totalSumRow;

    @JsonIgnore
    private Integer page;

    // 페이지당 ROW 건수
    @JsonIgnore
    private Integer pageRowCount;

    // 화면에 보여지는 페이지 블록 수
    @JsonIgnore
    private int pageBlockSize;

    // 페이지 블록의 시작 페이지번호
    @JsonIgnore
    private int pageBlockStartNo;

    // 페이지 블록의 종료 페이지번호
    @JsonIgnore
    private int pageBlockFinishNo;

    // 전체 데이터 건수
    @JsonIgnore
    private long totalRowCount;

    // 전체 페이지 수
    @JsonIgnore
    private int totalPageCount;

    public PageResponse(int page, int pageRowCount) {
        this.page = page;
        this.pageRowCount = pageRowCount;
        this.totalRowCount = 0;
    }

    public PageResponse(int page, int pageRowCount, List<T> list, long totalRowCount) {
        this.page = page;
        this.pageRowCount = pageRowCount;
        this.pageBlockSize = 10;
        this.rows = list;
        this.totalRowCount = totalRowCount;
        this.totalPageCount = getTotal();
        this.pageBlockStartNo = getStartPageNumber();
        this.pageBlockFinishNo = getFinishPageNumber();
    }

    public PageResponse(int page, int pageRowCount, List<T> list, long totalRowCount, T totalSumRow) {
        this.page = page;
        this.pageRowCount = pageRowCount;
        this.pageBlockSize = 10;
        this.rows = list;
        this.totalRowCount = totalRowCount;
        this.totalPageCount = getTotal();
        this.pageBlockStartNo = getStartPageNumber();
        this.pageBlockFinishNo = getFinishPageNumber();
        this.totalSumRow = totalSumRow;
    }


    /**
     * 전체 페이지 수 반환.
     * @return 전체 페이지 수
     */
    private int getTotal() {
        return (int) Math.ceil(this.totalRowCount*1d / pageRowCount);
    }


    /**
     * 게시판물 존재여부 반환.
     * @return 게시판물 존재여부
     */
    private boolean hasContents() {
        if(rows != null && rows.isEmpty() == false){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 페이징 시작 페이지 번호 넘기기.
     * @return 페이징 시작 페이지 번호
     */
    private int getStartPageNumber() {
        int pageNum = (int) Math.floor( (page -1) / pageBlockSize) * pageBlockSize + 1;
        return pageNum;
    }


    /**
     * 페이징 마지막 페이지 번호 넘기기.
     * @return 페이징 마지막 페이지 번호
     */
    private int getFinishPageNumber() {
        int pageNum = this.getStartPageNumber() + pageBlockSize -1;

        if (this.getTotal() > pageNum) {
            return pageNum;
        } else {
            return this.getTotal();
        }
    }


    /**
     * 목록 시작번호.
     * @return 목록 시작번호
     */
    private long getStartRowNumer() {
        if(this.hasContents() == true) {
            if(this.totalRowCount < this.pageRowCount) {
                return this.totalRowCount;
            } else {
                return this.totalRowCount - ((getPage()-1) * this.pageRowCount);
            }
        } else {
            return 0L;
        }
    }


    /**
     * 이전 페이지 번호 넘기기.
     * @return 이전 페이지 번호
     */
    private Integer getPrePageNumber(){
        if (this.getStartPageNumber() == 1) {
            return null;
        } else {
            return getStartPageNumber() - 1;
        }
    }


    /**
     * 다음 페이지 번호 넘기기.
     * @return 다음 페이지 번호
     */
    private Integer getNextPageNumber(){
        if (this.getTotal() > getFinishPageNumber()) {
            return getFinishPageNumber() + 1;
        } else {
            return null;
        }
    }

    /**
     * 페이지 정보만 조회
     * @return
     */
    public PageObject getPaging() {
        PageObject pageObject = new PageObject();
        pageObject.setCurPage(this.page);
        pageObject.setPageRowCount(this.pageRowCount);
        pageObject.setPageBlockSize(this.pageBlockSize);
        pageObject.setPageBlockStartNo(this.pageBlockStartNo);
        pageObject.setPageBlockFinishNo(this.pageBlockFinishNo);
        pageObject.setTotalRowCount(this.totalRowCount);
        pageObject.setTotalPageCount(this.totalPageCount);

        List<Integer> pageBlocks = new ArrayList<>();
        if(this.totalRowCount > 0) {
            for(int i = this.pageBlockStartNo; i <= this.pageBlockFinishNo; i++) {
                pageBlocks.add(i);
            }
            pageObject.setPageBlocks(pageBlocks);
        }

        // 이전 화살표
        if(this.pageBlockStartNo == 1) {
            pageObject.setPageBlockStartNo4Back(1);
        } else {
            pageObject.setPageBlockStartNo4Back(this.pageBlockStartNo-this.pageBlockSize);
        }

        // 다음 화살표
        if(this.pageBlockFinishNo == this.totalPageCount) {
            pageObject.setPageBlockStartNo4Next(this.totalPageCount);
        } else {
            pageObject.setPageBlockStartNo4Next(this.pageBlockFinishNo+1);
        }

        return pageObject;
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create().toJson(this);
    }
}
