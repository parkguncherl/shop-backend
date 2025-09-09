package com.shop.core.biz.mypage.vo.request;

import com.shop.core.entity.PartnerPrint;
import com.shop.core.entity.Notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Description: 마이페이지 Request
 * Date: 2023/03/15 14:45 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Schema(name = "MypageRequest")
public class MypageRequest {

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "MypagePrintSetUpdateRequest", description = "마이페이지 전표설정 수정 Request")
    public static class MypagePrintSetUpdateRequest extends PartnerPrint {

        @Schema(description = "샘플동일상품 정보인쇄여부")
        private String samplePrnYn;

        @Schema(description = "혼용율 인쇄코드")
        private String compPrnCd;

    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "PartnerPrintRequest", description = "전표설정 Request")
    public static class PartnerPrintRequest extends PartnerPrint {

        public PartnerPrint toEntity() {
            return PartnerPrint.builder()
                    .id(getId())
                    .partnerId(getPartnerId())
                    .fileId(getFileId())
                    .logoprintyn(getLogoprintyn())
                    .logoLocCd(getLogoLocCd())
                    .titleYn(getTitleYn())
                    .titleMng(getTitleMng())
                    .titleNor(getTitleNor())
                    .topYn(getTopYn())
                    .topMng(getTopMng())
                    .topNor(getTopNor())
                    .bottomYn(getBottomYn())
                    .bottomMng(getBottomMng())
                    .bottomNor(getBottomNor())
                    .creUser(getCreUser())
                    .updUser(getUpdUser())
                    .build();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    @Schema(name = "StickerDataRequest", description = "스티커 데이터 Request")
    public static class StickerDataRequest {
        private String noticeCd;
        private String title;
        private String noticeCntn;
        private String creUser;

        public Notice toEntity() {
            return Notice.builder()
                    .noticeCd(this.noticeCd)
                    .title(this.title)
                    .noticeCntn(this.noticeCntn)
                    .creUser(this.creUser)
                    .build();
        }
    }
}
