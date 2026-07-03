package com.shop.core.biz.notice.vo.response;

import com.shop.core.entity.Notice;
import com.shop.core.enums.BooleanValueCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "공지사항 응답 VO")
public class NoticeResponse {

    @Schema(description = "공지사항 ID")
    private Integer id;

    @Schema(description = "공지사항 코드")
    private String noticeCd;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "이동 URI")
    private String moveUri;

    @Schema(description = "게시 여부")
    private String gesiYn;

    @Schema(description = "생성자")
    private String creUser;

    @Schema(description = "생성일시")
    private LocalDate creTm;

    @Schema(description = "수정자")
    private String updUser;

    @Schema(description = "수정일시")
    private LocalDate updTm;

    public static NoticeResponse fromEntity(Notice notice) {
        NoticeResponse response = new NoticeResponse();
        response.setId(notice.getId());
        response.setNoticeCd(notice.getNoticeCd());
        response.setTitle(notice.getTitle());
        response.setMoveUri(notice.getMoveUri());
        response.setGesiYn(notice.getGesiYn());
        response.setCreUser(notice.getCreUser());
        response.setUpdUser(notice.getUpdUser());
        return response;
    }

    @Getter
    @Setter
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

        @Schema(description = "이동 URI")
        private String moveUri;

        @Schema(description = "게시 여부")
        private String gesiYn;

        @Schema(description = "생성자")
        private String creUser;

        @Schema(description = "생성일시")
        private LocalDate creTm;

        @Schema(description = "수정자")
        private String updUser;

        @Schema(description = "수정일시")
        private LocalDate updTm;

        @Schema(description = "총 페이지 수")
        private Integer totalRowCount;
    }

    @Getter
    @Setter
    @Schema(name = "NoticeResponse", description = "공지사항 상세 조회 응답", type = "object")
    public static class Detail extends NoticeResponse {
        public static Detail fromEntity(Notice notice) {
            Detail response = new Detail();
            response.setId(notice.getId());
            response.setNoticeCd(notice.getNoticeCd());
            response.setTitle(notice.getTitle());
            response.setMoveUri(notice.getMoveUri());
            response.setGesiYn(notice.getGesiYn());
            response.setCreUser(notice.getCreUser());
            response.setUpdUser(notice.getUpdUser());
            return response;
        }
    }

    /**
     * 팝업 공지사항 응답 클래스
     */
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(description = "팝업 공지사항 응답")
    public static class PopupNotice {

        @Schema(description = "공지사항 ID")
        private Integer id;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "파일 ID")
        private Integer fileId;

        @Schema(description = "시스템 파일명")
        private String sysFileNm;

        @Schema(description = "이동 URI")
        private String moveUri;

        @Schema(description = "게시 여부")
        private String gesiYn;

        @Schema(description = "생성자")
        private String creUser;

        @Schema(description = "생성일시")
        private LocalDateTime creTm;

        public static PopupNotice fromEntity(Notice notice) {
            PopupNotice response = new PopupNotice();
            response.setId(notice.getId());
            response.setTitle(notice.getTitle());
            response.setFileId(notice.getFileId());
            response.setSysFileNm(notice.getSysFileNm());
            response.setMoveUri(notice.getMoveUri());
            response.setGesiYn(notice.getGesiYn());
            response.setCreUser(notice.getCreUser());
            response.setCreTm(notice.getCreTm());
            return response;
        }
    }

    /**
     * 스티커 정보 응답 클래스
     */
    @Getter
    @Setter
    @Schema(description = "스티커 정보 응답")
    public static class Sticker {
        @Schema(description = "공지사항 ID")
        private Integer id;

        @Schema(description = "공지사항 코드")
        private String noticeCd;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "수정자")
        private String updUser;
    }
}