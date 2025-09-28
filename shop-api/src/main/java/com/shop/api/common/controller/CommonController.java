package com.shop.api.common.controller;

import com.shop.api.annotation.JwtUser;
import com.shop.api.common.service.CommonService;
import com.shop.core.biz.common.dao.FileDao;
import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.biz.common.vo.request.GridRequest;
import com.shop.core.biz.common.vo.response.CommonResponse;
import com.shop.core.biz.common.vo.response.GridResponse;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.entity.FileDet;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.core.utils.CommUtil;
import com.microsoft.azure.storage.file.CloudFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
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
    private final FileDao fileDao;

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
     * 파일_다운로드
     *
     * @param jwtUser
     * @param response
     * @param commonRequest
     */
    @GetMapping(value = "/file/download")
    @Operation(summary = "파일 다운로드")
    public void fileDownload(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(hidden = true) HttpServletResponse response,
            @Parameter(description = "파일 다운로드 Request") CommonRequest.FileDownload commonRequest
    ) {
        try {
            // 파일_다운로드
            OutputStream out = response.getOutputStream();
            CloudFile cloudFile = commonService.fileDownload(commonRequest);
            cloudFile.download(out);
            String fileName = commonRequest.getFileNm();

            if(fileName == null ){
                fileName = cloudFile.getName();
            } else {
                fileName = CommUtil.getFileName(commonRequest.getFileNm());
            }
            response.setHeader("Content-Disposition", "attachement; filename=\"" + fileName + "\";charset=\"UTF-8\"");

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 파일_삭제 (개별)
     *
     * @param jwtUser
     * @param fileId
     * @param fileSeq
     * @return
     */
    @DeleteMapping(value = "/fileDeleteBySeq/{fileId}/{fileSeq}")
    @Operation(summary = "파일 삭제")
    public ApiResponse fileDeleteBySeq(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @PathVariable Integer fileId, @PathVariable Integer fileSeq
    ) {
        Integer result = fileDao.deleteFileDetByUk(fileId, fileSeq, jwtUser);
        if(result == 0){
            return new ApiResponse<>(ApiResultCode.FAIL, "["+fileId+ "/"+ fileSeq+"]파일삭제가 실패하였습니다.");
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 파일_삭제 (개별)
     *
     * @param jwtUser
     * @param commonRequest
     * @return
     */
    @DeleteMapping(value = "/fileDeleteByKey")
    @Operation(summary = "파일 삭제")
    public ApiResponse fileDeleteByKey(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "CommonRequestFileDeleteByKey", description = "파일 업로드 Request", in = ParameterIn.QUERY) CommonRequest.FileDelete commonRequest
    ) {
        if(commonRequest.getFileId() == null || commonRequest.getFileId() <= 0){
            throw new CustomRuntimeException(ApiResultCode.FAIL_CREATE, "파일id 가 입력되지 않았습니다.");
        }

        if(StringUtils.isEmpty(commonRequest.getKey())){
            throw new CustomRuntimeException(ApiResultCode.FAIL_CREATE, "파일 key 가 입력되지 않았습니다.");
        }

        CommonResponse.SelectFile selectFile = fileDao.selectFileDet(commonRequest.getFileId(), null, commonRequest.getKey());

        if(selectFile == null){
            throw new CustomRuntimeException(ApiResultCode.FAIL_CREATE, "삭제할 파일정보가 존재하지 않습니다.");
        }

        Integer result = fileDao.deleteFileDetByKey(commonRequest.getFileId(), selectFile.getSysFileNm(), jwtUser);
        if(result == 0){
            return new ApiResponse<>(ApiResultCode.FAIL, "["+commonRequest.getFileId()+ "/"+ selectFile.getFileSeq()+"]파일삭제가 실패하였습니다.");
        }
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    @PostMapping("/grid-column/update")
    @Operation(summary = "그리드 컬럼 설정 변경")
    public ApiResponse<Integer> setUpGridColumn(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody GridRequest request
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, commonService.setUpGridColumn(request, jwtUser));
    }

    @PostMapping("/grid-column/init")
    @Operation(summary = "그리드 컬럼 설정 초기화(삭제)")
    public ApiResponse<Integer> deleteUpGridColumn(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestBody GridRequest request
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, commonService.deleteGridColum(request, jwtUser));
    }

    @GetMapping("/grid-column")
    @Operation(summary = "그리드 컬럼 설정 변경")
    public ApiResponse<GridResponse> setUpGridColumn(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @RequestParam("uri") String uri
    ) {
        return new ApiResponse<>(ApiResultCode.SUCCESS, commonService.getGridColumn(uri, jwtUser));
    }
}
