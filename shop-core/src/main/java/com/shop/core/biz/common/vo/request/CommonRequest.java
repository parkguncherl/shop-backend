package com.shop.core.biz.common.vo.request;

import com.shop.core.entity.FileDet;
import com.shop.core.interfaces.RequestFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.List;

/**
 * <pre>
 * Description: 공통 Request
 * Date: 2023/03/02 11:58 AM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Schema(name = "CommonRequest")
public class CommonRequest {

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema(name = "CommonRequestSelectFile", description = "파일 조회 요청 파라미터")
    public static class SelectFile implements RequestFilter {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "시퀀스")
        private Integer fileSeq;
    }

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema(name = "CommonRequestDeleteFile", description = "파일 삭제 요청 파라미터")
    public static class DeleteFile implements RequestFilter {

        @Schema(description = "아이디")
        private Integer id;

        @Schema(description = "시퀀스")
        private Integer fileSeq;

        @Schema(description = "수정자")
        protected String updUser;

        public FileDet toEntity() {
            return FileDet.builder()
                .id(getId())
                .fileSeq(getFileSeq())
                .updUser(getUpdUser())
                .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CommonRequestFileDownload", description = "파일 다운로드 요청 파라미터")
    public static class FileDownload {

        @Schema(description = "파일_아이디")
        private Integer id;

        @Schema(description = "파일_시퀀스")
        private Integer fileSeq;

        @Schema(description = "파일_명")
        private String fileNm;
    }

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema(name = "CommonRequestFileUpload", description = "파일 업로드 요청 파라미터")
    public static class FileUpload {

        @Schema(description = "파일 id (정보가 있으면 업데이트) ")
        private Integer fileId;

        @Schema(description = "파일 유형")
        private String fileType;

        @Schema(description = "파일 업로드")
        MultipartFile uploadFile;
    }

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema(name = "CommonRequestFileDelete", description = "파일 삭제 요청 파라미터")
    public static class FileDelete {

        @Schema(description = "파일 id (정보가 있으면 업데이트) ")
        private Integer fileId;

        @Schema(description = "파일 key")
        private String key;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @Schema(name = "CommonRequestFileUploads", description = "다중 파일 업로드 요청 파라미터")
    public static class FileUploads {

        @Schema(description = "파일 id (정보가 있으면 append 없으면 신규) ")
        private Integer fileId;

        @Schema(description = "파일 유형")
        private String fileType;

        @Schema(description = "다중 파일 업로드")
        List<MultipartFile> uploadFiles;
    }
}
