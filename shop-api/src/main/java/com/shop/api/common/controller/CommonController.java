package com.shop.api.common.controller;

import com.shop.api.annotation.AccessLog;
import com.shop.api.annotation.JwtUser;
import com.shop.api.biz.system.service.UserService;
import com.shop.api.common.service.CommonService;
import com.shop.api.common.service.FileService;
import com.shop.core.biz.common.dao.FileDao;
import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.biz.common.vo.response.CommonResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.FileDet;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.exception.CustomRuntimeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * <pre>
 * Description: 공통 Controller
 * Date :
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/common")
@Tag(name = "CommonController", description = "공통서비스 관련 API(엑셀템플릿 등)")
public class CommonController {

    private final CommonService commonService;
    private final UserService userService;
    private final FileDao fileDao;
    private final FileService fileService;

    /**
     * 개별_파일_조회
     */
    @GetMapping(value = "/file")
    @Operation(summary = "개별 파일 조회")
    public ApiResponse<CommonResponse.SelectFile> selectFileByUk(
        @Parameter(name = "CommonRequestSelectFile", description = "개별 파일 조회 Request", in = ParameterIn.PATH) CommonRequest.SelectFile commonRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(commonRequest.getId().toString())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        FileDet file = new FileDet();
        file.setId(commonRequest.getId());
        file.setFileSeq(1);

        // 파일_조회 (by Uk)
        CommonResponse.SelectFile selectFile = commonService.selectFileDet(file);

        return new ApiResponse<>(ApiResultCode.SUCCESS, selectFile);
    }

    /**
     * 파일_목록_조회 (by ID)
     *
     * @param jwtUser
     * @param fileId
     * @return
     */
    @GetMapping(value = "/file/{fileId}")
    @Operation(summary = "파일 목록 조회")
    public ApiResponse<List<FileDet>> selectFileList(
        @Parameter(hidden = true) @JwtUser User jwtUser,
        @PathVariable Integer fileId
    ) {
        // 파일_목록_조회 (by ID)
        List<FileDet> fileList = commonService.selectFileList(fileId);

        return new ApiResponse<>(ApiResultCode.SUCCESS, fileList);
    }

    /**
     * 파일_업로드 단건업로드는 기존파일이 있으면 삭제후 업로드 한다.
     *
     * @param jwtUser
     * @param commonRequest
     */
    @PostMapping(value = "/file/upload")
    @Operation(summary = "파일 단건 업로드")
    public ApiResponse<CommonResponse.FileDown> fileUpload(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "CommonRequestFileUpload", description = "파일 업로드 Request", in = ParameterIn.PATH) CommonRequest.FileUpload commonRequest
    ) throws IOException {
        CommonResponse.FileDown fileDown = commonService.fileUpload(commonRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, fileDown);
    }

    /**
     * 다중_파일_업로드
     *
     * @param jwtUser
     * @param commonRequest
     */
    @PostMapping(value = "/file/uploads")
    @Operation(summary = "다중 파일 업로드")
    public ApiResponse<List<CommonResponse.FileDown>> fileUploads(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "CommonRequestFileUploads", description = "다중 파일 업로드 Request", in = ParameterIn.PATH) CommonRequest.FileUploads commonRequest
    ) throws IOException {
        List<CommonResponse.FileDown> fileDownList = commonService.fileUploads(commonRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, fileDownList);
    }


    /**
     * 다중_파일_업로드
     *
     * @param jwtUser
     * @param commonRequest
     */
    @PostMapping(value = "/imgfile/uploads")
    @Operation(summary = "다중 파일 업로드")
    public ApiResponse<List<CommonResponse.FileDown>> imgFileUploads(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "CommonRequestFileUploads", description = "다중 파일 업로드 Request", in = ParameterIn.PATH) CommonRequest.FileUploads commonRequest
    ) throws IOException {
        List<CommonResponse.FileDown> fileDownList = commonService.imgFileUploads(commonRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS, fileDownList);
    }



    /**
     * 단일 이미지파일 수정
     */
    @PatchMapping(value = "/imgfile/update")
    @Operation(summary = "단일 이미지파일 수정")
    public ApiResponse<ApiResultCode> imageFileUpdate(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "CommonRequestFileUpdate", description = "단일 이미지파일 수정 Request", in = ParameterIn.PATH) CommonRequest.FileUpdate fileUpdate
    ) {
        // 필수값 체크
        if (fileUpdate.getFileId() == null || fileUpdate.getFileSeq() <= 0) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE, "파일 id 혹은 seq 의 입력 여부를 확인하십시요.");
        }

        commonService.imageFileUpdate(fileUpdate, jwtUser);

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }


    /**
     * 파일_삭제 (개별)
     *
     * @param jwtUser
     * @param fileId
     * @param fileSeq
     * @return
     */
    @DeleteMapping(value = "/fileDeleteBySeqWithBuket/{fileId}/{fileSeq}")
    @Operation(summary = "파일 삭제")
    public ApiResponse<ApiResultCode> deleteFileDetByUkWithBuket(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer fileId, @PathVariable Integer fileSeq
    ) {
        commonService.deleteFileDetByUkWithBuket(fileId, fileSeq, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }


    /**
     * 파일_삭제 (개별)
     *
     * @param jwtUser
     * @param fileDetId
     * @return
     */
    @DeleteMapping(value = "/fileDeleteByKey/{fileDetId}")
    @Operation(summary = "파일 삭제")
    public ApiResponse fileDeleteByKey(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer fileDetId
    ) {
        commonService.deleteFileDetByIdWithBuket(fileDetId, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * s3(cloudFlare) 파일정보
     *
     * @param fileKey
     * @return String
     */
    @AccessLog("파일url 적용")
    @GetMapping("/getFileUrl")
    @Operation(summary = "혼용율, 샘플전표 스큐정보 조회")
    public ApiResponse<String> getFileUrl(CommonRequest.FileKey fileKey) {
        String resultUrl = commonService.getFileUrl(fileKey.getFileKey());
        return new ApiResponse<>(resultUrl);
    }

    /**
     * 파일_재정렬 (seq to seq)
     *
     * @param jwtUser
     * @param fileRearrangementRequest
     * @return
     */
    @PatchMapping(value = "/rearrangeFilesByStepsToMove")
    @Operation(summary = "파일 재정렬")
    public ApiResponse<Void> rearrangeFilesByStepsToMove(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody CommonRequest.FileRearrangementRequest fileRearrangementRequest
    ) {
        commonService.rearrangeFilesByStepsToMove(fileRearrangementRequest, jwtUser);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }
}
