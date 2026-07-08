package com.shop.core.biz.notice.vo.response;

import com.shop.core.entity.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공지사항 응답 VO")
public class NoticeResponse extends Notice {

    @Schema(description = "첨부파일 수")
    private Integer fileCnt;

    public static NoticeResponse fromEntity(Notice notice) {
        NoticeResponse response = new NoticeResponse();
        copyFields(notice, response);
        return response;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "공지사항 페이징 응답")
    public static class Paging extends NoticeResponse {

        public static Paging fromEntity(Notice notice) {
            Paging response = new Paging();
            copyFields(notice, response);
            return response;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "공지사항 상세 조회 응답")
    public static class Detail extends NoticeResponse {

        public static Detail fromEntity(Notice notice) {
            Detail response = new Detail();
            copyFields(notice, response);
            return response;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "팝업 공지사항 응답")
    public static class PopupNotice extends NoticeResponse {

        public static PopupNotice fromEntity(Notice notice) {
            PopupNotice response = new PopupNotice();
            copyFields(notice, response);
            return response;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "스티커 정보 응답")
    public static class Sticker extends NoticeResponse {

        public static Sticker fromEntity(Notice notice) {
            Sticker response = new Sticker();
            copyFields(notice, response);
            return response;
        }
    }

    // ── 공통 필드 복사 ──────────────────────────────────────
    protected static void copyFields(Notice src, NoticeResponse dst) {
        dst.setId(src.getId());
        dst.setPartnerId(src.getPartnerId());
        dst.setNoticeCd(src.getNoticeCd());
        dst.setTitle(src.getTitle());
        dst.setMoveUri(src.getMoveUri());
        dst.setFileId(src.getFileId());
        dst.setGesiYn(src.getGesiYn());
        dst.setSysFileNm(src.getSysFileNm());
        dst.setCreTm(src.getCreTm());
        dst.setCreUser(src.getCreUser());
        dst.setUpdTm(src.getUpdTm());
        dst.setUpdUser(src.getUpdUser());
    }
}
