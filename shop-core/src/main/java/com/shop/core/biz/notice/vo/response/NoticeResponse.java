package com.shop.core.biz.notice.vo.response;

import com.shop.core.entity.Notice;
import com.shop.core.enums.BooleanValueCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "공지사항 응답 VO")
public class NoticeResponse {

    @Schema(description = "공지사항 ID")
    private Integer id;

    @Schema(description = "공지사항 코드")
    private String noticeCd;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "공지사항 내용")
    private String noticeCntn;

    @Schema(description = "이동 URI")
    private String moveUri;

    @Schema(description = "권한 코드")
    private String authCds;

    @Schema(description = "삭제 여부")
    private String delYn;

    @Schema(description = "조회수")
    private String readCnt;

    @Schema(description = "생성자")
    private String creUser;

    @Schema(description = "생성일시")
    private LocalDateTime creTm;

    @Schema(description = "수정자")
    private String updUser;

    @Schema(description = "수정일시")
    private LocalDateTime updTm;

    public static NoticeResponse fromEntity(Notice notice) {
        NoticeResponse response = new NoticeResponse();
        response.setId(notice.getId());
        response.setNoticeCd(notice.getNoticeCd());
        response.setTitle(notice.getTitle());
        response.setNoticeCntn(notice.getNoticeCntn());
        response.setMoveUri(notice.getMoveUri());
        response.setAuthCds(notice.getAuthCds());
        response.setDelYn(notice.getDelYn());
        response.setCreUser(notice.getCreUser());
        response.setCreTm(notice.getCreTm());
        response.setUpdUser(notice.getUpdUser());
        response.setUpdTm(notice.getUpdTm());
        return response;
    }

    @Data
    @Schema(description = "공지사항 페이징 응답")
    public static class Paging {

        @Schema(description = "공지사항 No")
        private Integer no;

        @Schema(description = "공지사항 ID")
        private Integer id;

        @Schema(description = "공지사항 코드")
        private String noticeCd;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "공지사항 내용")
        private String noticeCntn;

        @Schema(description = "이동 URI")
        private String moveUri;

        @Schema(description = "권한 코드")
        private String authCds;

        @Schema(description = "삭제 여부")
        private String delYn;

        @Schema(description = "조회수")
        private String readCnt;

        @Schema(description = "생성자")
        private String creUser;

        @Schema(description = "생성일시")
        private LocalDateTime creTm;

        @Schema(description = "수정자")
        private String updUser;

        @Schema(description = "수정일시")
        private LocalDateTime updTm;

        @Schema(description = "총 페이지 수")
        private Integer totalRowCount;
    }

    @Data
    @Schema(name = "NoticeResponse", description = "공지사항 상세 조회 응답", type = "object")
    public static class Detail extends NoticeResponse {
        public static Detail fromEntity(Notice notice) {
            Detail response = new Detail();
            response.setId(notice.getId());
            response.setNoticeCd(notice.getNoticeCd());
            response.setTitle(notice.getTitle());
            response.setNoticeCntn(notice.getNoticeCntn());
            response.setMoveUri(notice.getMoveUri());
            response.setAuthCds(notice.getAuthCds());
            response.setDelYn(notice.getDelYn());
            response.setCreUser(notice.getCreUser());
            response.setCreTm(notice.getCreTm());
            response.setUpdUser(notice.getUpdUser());
            response.setUpdTm(notice.getUpdTm());
            return response;
        }
    }

    /**
     * 스티커 정보 응답 클래스
     */
    @Data
    @Schema(description = "스티커 정보 응답")
    public static class Sticker {
        @Schema(description = "공지사항 ID")
        private Integer id;

        @Schema(description = "공지사항 코드")
        private String noticeCd;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "공지사항 내용")
        private String noticeCntn;

        @Schema(description = "수정자")
        private String updUser;
    }
}