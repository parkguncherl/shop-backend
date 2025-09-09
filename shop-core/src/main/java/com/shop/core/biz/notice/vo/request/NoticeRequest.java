package com.shop.core.biz.notice.vo.request;

import com.shop.core.entity.Factory;
import com.shop.core.entity.Notice;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class NoticeRequest {

    @Getter
    @Setter
    @Schema(description = "공지사항 검색 요청")
    public static class PagingFilter extends Notice implements RequestFilter {

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "검색 시작일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate startDate;

        @Schema(description = "검색 종료일")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate endDate;

        @Schema(description = "검색어")
        private String searchKeyword;
    }

    @Getter
    @Setter
    @Schema(name = "NoticeRequestCreate", description = "공지사항 생성 요청")
    public static class Create extends Notice {
    }

    @Getter
    @Setter
    @Schema(name = "NoticeRequestUpdate", description = "공지사항 수정 요청")
    public static class Update {
        @Schema(description = "공지사항 ID")
        private Integer id;

        @Schema(description = "공지사항 코드")
        private String noticeCd;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "내용")
        private String noticeCntn;

        @Schema(description = "이동 URI")
        private String moveUri;

        @Schema(description = "권한 코드")
        private String authCds;

        @Schema(description = "수정자")
        private String updUser;
    }

    @Getter
    @Setter
    @Schema(description = "공지사항 삭제 요청")
    public static class Delete {
        @Schema(description = "공지사항 ID")
        private Integer id;

        @Schema(description = "수정자")
        private String updUser;
    }

    /**
     * 공지사항 상세 조회 요청을 위한 클래스
     */
    @Getter
    @Setter
    @Schema(description = "공지사항 상세 조회 요청")
    public static class DetailFilter {
        @Schema(description = "공지사항 ID")
        private Integer noticeId;

        @Schema(description = "파트너 ID")
        private Integer partnerId;

        @Schema(description = "조회수")
        private String readCnt;
    }
    @Getter
    @Setter
    @Schema(description = "스티커 정보 수정 요청")
    public static class StickerUpdate {
        @Schema(description = "공지사항 내용")
        private String noticeCntn;

        @Schema(description = "수정자")
        private String updUser;

        @Schema(description = "파트너 ID")
        private String partnerId;
    }
}
