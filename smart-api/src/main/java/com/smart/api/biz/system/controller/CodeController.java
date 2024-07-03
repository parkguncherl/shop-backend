package com.smart.api.biz.system.controller;

import com.smart.api.annotation.AccessLog;
import com.smart.api.annotation.JwtUser;
import com.smart.api.biz.system.service.CodeService;
import com.smart.api.biz.system.service.UserService;
import com.smart.core.annotations.NotAuthRequired;
import com.smart.core.biz.common.vo.request.PageRequest;
import com.smart.core.biz.common.vo.response.PageResponse;
import com.smart.core.biz.system.vo.request.CodeRequest;
import com.smart.core.biz.system.vo.response.ApiResponse;
import com.smart.core.biz.system.vo.response.CodeResponse;
import com.smart.core.entity.Code;
import com.smart.core.entity.User;
import com.smart.core.enums.*;
import com.smart.core.utils.CommUtil;
import com.smart.core.utils.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Description : 코드_관리 Controller
 * Date : 2023/02/06 11:56 AM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/code")
@Tag(name = "CodeController", description = "코드 관련 API")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @Autowired
    private UserService userService;

    /**
     * 코드관리_목록_조회 (페이징)
     *
     * @param jwtUser
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("코드관리 목록 조회")
    @GetMapping(value = "/paging")
    @Operation(summary = "코드관리 목록 조회 (페이징)")
    public ApiResponse<PageResponse<CodeResponse.Paging>> selectCodePaging(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "CodeRequestPagingFilter", description = "코드관리 목록 조회 (페이징) 필터", in = ParameterIn.PATH) CodeRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "코드관리 목록 조회 페이징") PageRequest<CodeRequest.PagingFilter> pageRequest
    ) {
        // 상위코드가 없을 시, default 값 셋팅
        if (StringUtils.isEmpty(filter.getCodeUpper())) {
            filter.setCodeUpper("TOP");
        }

        pageRequest.setFilter(filter);

        // 코드관리_목록_조회 (페이징)
        PageResponse<CodeResponse.Paging> response = codeService.selectCodePaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }

    /**
     * 코드_목록_조회
     *
     * @return
     */
    @GetMapping()
    @Operation(summary = "코드 목록 조회")
    public ApiResponse<List<CodeResponse.Select>> selectCodeList() {

        // 코드_목록_조회
        List<CodeResponse.Select> codeList = codeService.selectCodeList();

        return new ApiResponse<>(ApiResultCode.SUCCESS, codeList);
    }

    /**
     * 코드_콤보_조회 (by CodeUpper)
     *
     * @param codeRequest
     * @return
     */
    @GetMapping(value = "/dropdown")
    @Operation(summary = "코드 콤보 조회")
    public ApiResponse<List<CodeResponse.CodeDropDown>> selectDropdownByCodeUpper(
            @Parameter(description = "코드 DropDown Request") CodeRequest.CodeDropDown codeRequest,
            @Parameter(hidden = true) @JwtUser User jwtUser
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getCodeUpper())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        /*
        if (!StringUtils.equals("KR", CommUtil.getCountryCookie())) { // 미국으로 로그인한 경우 지역코드 변경
            if (StringUtils.equals(GlobalConst.KO_REGION_CODE_UPPER.getCode(), codeRequest.getCodeUpper())) {
                codeRequest.setCodeUpper(GlobalConst.US_REGION_CODE_UPPER.getCode());
            }
        }
        */

        codeRequest.setCountryCode(CommUtil.getCountryCookie());
        // 코드_콤보_조회 (by CodeUpper)
        List<CodeResponse.CodeDropDown> codeList = codeService.selectDropdownByCodeUpper(codeRequest);

        if (codeList.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_CODE);
        }

        // 권한코드의 경우 콤보는 본인권한보다 작거나 같은것을 보여준다.
        if (StringUtils.equals("S0002", codeRequest.getCodeUpper())) {
            List<CodeResponse.CodeDropDown> authCodeList = new ArrayList<>();
            String authCd = userService.selectUserById(jwtUser.getId()).getAuthCd();

            for (CodeResponse.CodeDropDown vo : codeList) {
                if (StringUtils.isNumeric(vo.getCodeCd()) && Integer.parseInt(authCd) >= Integer.parseInt(vo.getCodeCd())) {
                    authCodeList.add(vo);
                }
            }
            return new ApiResponse<>(authCodeList);
        }

        return new ApiResponse<>(codeList);
    }

    /**
     * 코드_조회 (by Id)
     *
     * @param codeId
     * @return
     */
    @GetMapping(value = "/{codeId}")
    @Operation(summary = "코드 조회")
    public ApiResponse<CodeResponse.Select> selectCodeById(
            @Parameter(description = "코드_아이디") @PathVariable String codeId
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeId)) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        // 코드_조회 (by Id)
        CodeResponse.Select code = codeService.selectCodeById(Integer.valueOf(codeId));

        if (code == null) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_CODE);
        }

        return new ApiResponse<>(code);
    }

    /**
     * 하위_코드_조회 (by codeUpper)
     *
     * @param codeUpper
     * @return
     */
    @GetMapping(value = "/lower/{codeUpper}")
    @Operation(summary = "하위 코드 조회")
    public ApiResponse<List<CodeResponse.LowerSelect>> selectLowerCodeByCodeUpper(
            @Parameter(description = "상위_코드") @PathVariable String codeUpper
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeUpper)) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        // 하위_코드_조회 (by codeUpper)
        List<CodeResponse.LowerSelect> codeList = codeService.selectLowerCodeByCodeUpperForCodeMng(codeUpper);

        return new ApiResponse<>(codeList);
    }

    /**
     * 국가_코드_콤보_조회 (by CodeUpper)
     *
     * @param codeRequest
     * @return
     */
    @NotAuthRequired
    @GetMapping(value = "/country")
    @Operation(summary = "국가 코드 콤보 조회")
    public ApiResponse<List<CodeResponse.CodeDropDown>> selectCountryDropdown(
            @Parameter(description = "코드 DropDown Request") CodeRequest.CodeDropDown codeRequest,
            @Parameter(hidden = true) @JwtUser User jwtUser
    ) {
        codeRequest.setCodeUpper("S0001");

        /*
        if (StringUtils.equals("US", CommUtil.getCountryCookie())) { // 미국으로 로그인한 경우 지역코드 변경
            if (StringUtils.equals(GlobalConst.KO_REGION_CODE_UPPER.getCode(), codeRequest.getCodeUpper())) {
                codeRequest.setCodeUpper(GlobalConst.US_REGION_CODE_UPPER.getCode());
            }
        }
        */

        codeRequest.setCountryCode(CommUtil.getCountryCookie());

        // 코드_콤보_조회 (by CodeUpper)
        List<CodeResponse.CodeDropDown> codeList = codeService.selectDropdownByCodeUpper(codeRequest);

        if (codeList.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_CODE);
        }
        return new ApiResponse<>(codeList);
    }

    /**
     * 코드_등록
     *
     * @param jwtUser
     * @param codeRequest
     * @return
     */
    @AccessLog("코드 등록")
    @PostMapping()
    @Operation(summary = "코드 등록")
    public ApiResponse insertCode(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 등록 Request") @RequestBody CodeRequest.Create codeRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getCodeUpper()) || StringUtils.isEmpty(codeRequest.getCodeCd())
                || StringUtils.isEmpty(codeRequest.getCodeNm()) || StringUtils.isEmpty(codeRequest.getCodeOrder().toString())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        CommUtil.charValidate(EsseType.ESS, LangType.DIG, codeRequest.getCodeOrder() + "", 4, "순서");
        CommUtil.charValidate(EsseType.NUL, LangType.ENG, codeRequest.getCodeEtc2(), 0, "기타정보영문");

        // 국가 코드는 2자리만
        if (StringUtils.equals("S0001", codeRequest.getCodeUpper())) {
            // 자리수확인
            if (codeRequest.getCodeCd().length() != 2) {
                return new ApiResponse<>(ApiResultCode.COUNTRY_CODE_VALID);
            }
            // 대문자인지 확인
            if (!CommUtil.isStringUpperCase(codeRequest.getCodeCd())) {
                return new ApiResponse<>(ApiResultCode.COUNTRY_CODE_VALID);
            }
        }

        Code code = new Code();
        code.setCodeUpper(codeRequest.getCodeUpper());
        code.setCodeCd(codeRequest.getCodeCd());

        // 코드_조회 (by Uk)
        Code existCode = codeService.selectCodeByUkIncludeDelete(code);

        // 중복 코드 체크
        if (existCode != null) {
            return new ApiResponse<>(ApiResultCode.DUPLICATE_VALUE);
        }

        // 세션정보를 이용해서 등록자 세팅
        codeRequest.setCreateUser(jwtUser.getLoginId());

        // 코드_등록
        Integer insertCount = codeService.insertCode(codeRequest);

        if (insertCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_CREATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 코드_수정
     *
     * @param jwtUser
     * @param codeRequest
     * @return
     */
    @AccessLog("코드 수정")
    @PutMapping()
    @Operation(summary = "코드 수정")
    public ApiResponse updateCode(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 수정 Request") @RequestBody CodeRequest.Update codeRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getId().toString()) || StringUtils.isEmpty(codeRequest.getCodeUpper())
                || StringUtils.isEmpty(codeRequest.getCodeCd()) || StringUtils.isEmpty(codeRequest.getCodeNm())
                || StringUtils.isEmpty(codeRequest.getCodeOrder().toString())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }

        CommUtil.charValidate(EsseType.ESS, LangType.DIG, codeRequest.getCodeOrder() + "", 4, "순서");
        CommUtil.charValidate(EsseType.NUL, LangType.ENG, codeRequest.getCodeEtc2(), 0, "기타정보영문");

        CodeResponse.Select codeResponse = codeService.selectCodeById(codeRequest.getId());

        if (codeResponse == null) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_CODE);
        } else {
            if (!StringUtils.equals(codeResponse.getCodeCd(), codeRequest.getCodeCd())) {
                Code code = new Code();
                code.setCodeUpper(codeRequest.getCodeUpper());
                code.setCodeCd(codeRequest.getCodeCd());

                // 코드_조회 (by Uk)
                Code existCode = codeService.selectCodeByUkIncludeDelete(code);

                // 중복 코드 체크
                if (existCode != null) {
                    return new ApiResponse<>(ApiResultCode.DUPLICATE_VALUE);
                }
            }
        }

        // 세션정보를 이용해서 수정자 세팅
        codeRequest.setUpdateUser(jwtUser.getLoginId());

        // 코드_수정
        Integer updateCount = codeService.updateCode(codeRequest);

        if (updateCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_UPDATE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 코드_삭제
     *
     * @param jwtUser
     * @param codeRequest
     * @return
     */
    @AccessLog("코드 삭제")
    @DeleteMapping()
    @Operation(summary = "코드 삭제")
    public ApiResponse deleteCode(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "코드 삭제 Request") @RequestBody CodeRequest.Delete codeRequest
    ) {
        // 필수값 체크
        if (StringUtils.isEmpty(codeRequest.getId().toString())) {
            return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE);
        }
        Integer deleteCount = 0;
        if (StringUtils.equals("TOP", codeRequest.getCodeUpper())) {
            deleteCount = codeService.deleteCodesByCodeUpper(codeRequest.getCodeCd());
            deleteCount = deleteCount + codeService.deleteCodeRealUk(codeRequest.getCodeCd());
        } else {
            // 코드_삭제
            deleteCount = codeService.deleteCode(codeRequest);
        }

        if (deleteCount == 0) {
            return new ApiResponse<>(ApiResultCode.FAIL_DELETE);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 하위 코드 저장 (복수)
     *
     * @param jwtUser
     * @param codeRequestList
     * @return
     */
    @AccessLog("하위 코드 저장(복수)")
    @PutMapping("/lower/{codeUpper}")
    @Operation(summary = "하위 코드 저장 (복수)")
    public ApiResponse updateCodes(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(description = "상위_코드") @PathVariable String codeUpper,
            @Parameter(description = "하위 코드 저장 Request (복수)") @RequestBody List<CodeRequest.Create> codeRequestList
    ) {
        // 필수값 체크
        /*if (codeRequestList.isEmpty()) {
            return new ApiResponse<>(ApiResultCode.NOT_FOUND_CODE);
        }*/

        // validation 체크
        for (CodeRequest.Create code : codeRequestList) {
            // 국가 코드는 2자리만
            if (StringUtils.equals("S0001", code.getCodeUpper())) {
                // 자리수확인
                if (code.getCodeCd().length() != 2) {
                    return new ApiResponse<>(ApiResultCode.COUNTRY_CODE_VALID);
                }
                // 대문자인지 확인
                if (!CommUtil.isStringUpperCase(code.getCodeCd())) {
                    return new ApiResponse<>(ApiResultCode.COUNTRY_CODE_VALID);
                }
            }
        }

        try {
            // 하위_코드_저장 (복수)
            List<CodeResponse.LowerSelect> nowCodes = codeService.selectLowerCodeByCodeUpper(codeUpper);
            // 먼저 없는거 삭제한다.
            for (CodeResponse.LowerSelect lowerSelect : nowCodes) {
                boolean isDelete = true;
                for (CodeRequest.Create code : codeRequestList) {
                    if (StringUtils.equals(lowerSelect.getCodeCd(), code.getCodeCd())) {
                        isDelete = false;
                    }
                }
                if (isDelete) {
                    CodeRequest.Delete codeRequest = new CodeRequest.Delete();
                    codeRequest.setId(lowerSelect.getId());
                    codeService.deleteCode(codeRequest);
                }
            }
            Integer order = 1;
            // 있는거 변경사항 있는지 확인
            for (CodeRequest.Create code : codeRequestList) {
                code.setCodeOrder(order);
                code.setCreateUser(jwtUser.getLoginId());
                code.setCodeOrder(order);

                Code codeByUk = new Code();
                codeByUk.setCodeUpper(codeUpper);
                codeByUk.setCodeCd(code.getCodeCd());

                if (codeService.selectCodeByUkIncludeDelete(codeByUk) == null) {
                    codeService.insertCode(code);
                } else {
                    codeService.updateCodeByUk(code.toEntity());
                }
                order++;
            }

//            codeService.insertCodes(codeUpper, codeRequestList, jwtUser);
        } catch (Exception e) {
            return new ApiResponse<>(ApiResultCode.BAD_REQUEST);
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 코드관리 엑셀 다운로드
     *
     * @param response
     * @param jwtUser
     */
    @AccessLog("코드관리 엑셀 다운로드")
    @GetMapping(value = "/excel-down")
    @Operation(summary = "코드관리 엑셀 다운로드")
    public void excelDown(
            HttpServletResponse response,
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "CodeRequestPagingFilter", description = "코드관리 목록 조회 (페이징) 필터", in = ParameterIn.PATH) CodeRequest.PagingFilter filter
    ) {
        String excelTitle = "코드관리 엑셀 다운로드";
        String[] columnArray = ExcelTitleCode.CODE_EXCEL_COLUMNS.getCode().split("\\|"); // | 로 split

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet worksheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle(); // 셀 스타일을 위한 변수
        style.setAlignment(HorizontalAlignment.CENTER); // 글 위치를 중앙으로 설정

        // 새로운 sheet를 생성한다.
        // 가장 첫번째 줄에 제목을 만든다.
        XSSFRow row = worksheet.createRow(0);
        int columnIndex = 0;
        for (String colunmNm : columnArray) {
            row.createCell(columnIndex++).setCellValue(colunmNm);
        }

        // 사이즈 변경
        for (int x = 0; x < worksheet.getRow(0).getPhysicalNumberOfCells(); x++) {
            worksheet.autoSizeColumn(x);
            worksheet.setColumnWidth(x, (worksheet.getColumnWidth(x)) + 1200);
        }

        if (StringUtils.isEmpty(filter.getCodeUpper())) {
            filter.setCodeUpper("TOP");
        }

        PageRequest<CodeRequest.PagingFilter> pageRequest = new PageRequest<>();
        pageRequest.setFilter(filter);
        pageRequest.setCurPage(1);
        pageRequest.setPageRowCount(1000 * 1000);
        PageResponse<CodeResponse.Paging> pagingPageResponse = codeService.selectCodePaging(pageRequest);

        // 데이터 엑셀에 밀어넎기
        int rowIndex = 1;
        for (CodeResponse.Paging codeResponse : pagingPageResponse.getRows()) {
            columnIndex = 0;
            row = worksheet.createRow(rowIndex);
            row.createCell(columnIndex++).setCellValue(codeResponse.getCodeUpper());
            row.createCell(columnIndex++).setCellValue(codeResponse.getCodeCd());
            row.createCell(columnIndex++).setCellValue(codeResponse.getCodeNm());
            row.createCell(columnIndex++).setCellValue(codeResponse.getCodeEtc1());
            row.createCell(columnIndex++).setCellValue(codeResponse.getCodeDesc());
            row.createCell(columnIndex++).setCellValue(codeResponse.getCodeEtc1());
            row.createCell(columnIndex++).setCellValue(codeResponse.getCodeEtc2());
            row.createCell(columnIndex++).setCellValue(codeResponse.getCodeOrder());
            rowIndex++;
        }

        try {
            response.setContentType("application/vnd.ms-excel");
            String excelName = CommUtil.getFileName(excelTitle);
            response.setHeader("Content-Disposition", "attachement; filename=\"" + excelName + "\";charset=\"UTF-8\"");
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 코드 엑셀 업로드
     *
     * @param jwtUser
     * @param uploadFile
     * @return
     * @throws IOException
     */
    @AccessLog("코드관리 엑셀 업로드")
    @PostMapping(value = "/excel-upload")
    @Operation(summary = "코드관리 엑셀 업로드")
    public ApiResponse excelUpload(
            @Parameter(hidden = true) @JwtUser User jwtUser,
            @Parameter(name = "CommonRequest", description = "CommonRequest", in = ParameterIn.PATH) CodeRequest.ExcelUpload uploadFile
    ) throws IOException {
        String extention = FileUtil.getExtention(uploadFile.getUploadFile().getOriginalFilename());

        if (!StringUtils.equalsIgnoreCase("XLSX", extention)) {
            return new ApiResponse<>(ApiResultCode.FAIL_EXCEL_UPLOAD);
        }

        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(uploadFile.getUploadFile().getInputStream());
        } catch (Exception e){
            return new ApiResponse<>(ApiResultCode.FAIL_EXCEL_UPLOAD);
        }

        Sheet worksheet = workbook.getSheetAt(0);

        if (worksheet.getPhysicalNumberOfRows() < 2) {
            return new ApiResponse<>(ApiResultCode.FAIL_EXCEL_UPLOAD_NODATA);
        }

        Row headerRow = worksheet.getRow(0);
        headerRow.getCell(0).getStringCellValue();

        String[] columnArray = ExcelTitleCode.CODE_EXCEL_COLUMNS.getCode().split("\\|"); // | 로 split

        if (headerRow.getPhysicalNumberOfCells() != columnArray.length) {
            return new ApiResponse<>(ApiResultCode.FAIL_EXCEL_UPLOAD_NOTMATCH);
        }

        List<CodeRequest.Create> codeRequestList = new ArrayList<>();

        // 헤더 정보 확인
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            CodeRequest.Create code = new CodeRequest.Create();

            if (row.getCell(0) == null) {
                return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE, String.format("%d 번째 줄, 필수값 상위코드가 없습니다.", i));
            } else if (row.getCell(1) == null) {
                return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE, String.format("%d 번째 줄, 필수값 코드값이 없습니다.", i));
            } else if (row.getCell(2) == null) {
                return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE, String.format("%d 번째 줄, 필수값 코드명이 없습니다.", i));
            } else if (row.getCell(7) == null) {
                return new ApiResponse<>(ApiResultCode.NO_REQUIRED_VALUE, String.format("%d 번째 줄, 필수값 순서가 없습니다.", i));
            }

            if (row.getCell(0).getCellType() == CellType.STRING) {
                code.setCodeUpper(row.getCell(0).getStringCellValue());
            } else {
                return new ApiResponse<>(ApiResultCode.BAD_REQUEST, String.format("%d 번째 줄, 상위코드 값이 유효하지 않습니다.", i));
            }

            if (row.getCell(1).getCellType() == CellType.STRING) {
                code.setCodeCd(row.getCell(1).getStringCellValue());
            } else {
                return new ApiResponse<>(ApiResultCode.BAD_REQUEST, String.format("%d 번째 줄, 코드값이 유효하지 않습니다.["+row.getCell(2).getCellType()+"]", i));
            }

            if (row.getCell(2).getCellType() == CellType.STRING ) {
                code.setCodeNm(row.getCell(2).getStringCellValue());
            } else {
                return new ApiResponse<>(ApiResultCode.BAD_REQUEST, String.format("%d 번째 줄, 코드명이 유효하지 않습니다.", i));
            }

            if (row.getCell(3) != null && StringUtils.isNotEmpty(row.getCell(3).toString())) {
                if (row.getCell(4).getCellType() == CellType.STRING) {
                    code.setCodeEtc1(row.getCell(3).getStringCellValue());
                } else {
                    return new ApiResponse<>(ApiResultCode.BAD_REQUEST, String.format("%d 번째 줄, 영문코드명이 유효하지 않습니다.", i));
                }
            }

            if (row.getCell(4) != null && StringUtils.isNotEmpty(row.getCell(4).toString())) {
                if (row.getCell(4).getCellType() == CellType.STRING) {
                    code.setCodeDesc((row.getCell(4).getStringCellValue()));
                } else {
                    return new ApiResponse<>(ApiResultCode.BAD_REQUEST, String.format("%d 번째 줄, 설명 값이 유효하지 않습니다.", i));
                }
            }

            if (row.getCell(5) != null && StringUtils.isNotEmpty(row.getCell(5).toString())) {
                if (row.getCell(5).getCellType() == CellType.STRING) {
                    code.setCodeEtc1(row.getCell(5).getStringCellValue());
                } else {
                    return new ApiResponse<>(ApiResultCode.BAD_REQUEST, String.format("%d 번째 줄, 기타정보 값이 유효하지 않습니다.", i));
                }
            }

            if (row.getCell(6) != null && StringUtils.isNotEmpty(row.getCell(6).toString())) {
                if (row.getCell(6).getCellType() == CellType.STRING) {
                    code.setCodeEtc1(row.getCell(6).getStringCellValue());
                } else {
                    return new ApiResponse<>(ApiResultCode.BAD_REQUEST, String.format("%d 번째 줄, 기타정보영문 값이 유효하지 않습니다.", i));
                }
            }

            if (row.getCell(7).getCellType() == CellType.NUMERIC || row.getCell(7).getCellType() == CellType.STRING) {
                if (row.getCell(7).getCellType() == CellType.NUMERIC) {
                    code.setCodeOrder(Integer.valueOf((int) row.getCell(7).getNumericCellValue()));
                } else {
                    code.setCodeOrder(Integer.valueOf(row.getCell(7).getStringCellValue()));
                }
            } else {
                return new ApiResponse<>(ApiResultCode.BAD_REQUEST, String.format("%d 번째 줄, 순서 값이 유효하지 않습니다.", i));
            }

            Code codeUk = new Code();
            codeUk.setCodeUpper(code.getCodeUpper());
            codeUk.setCodeCd(code.getCodeCd());

            // 코드_조회 (by Uk)
            Code existCode = codeService.selectCodeByUk(codeUk);

            // 중복 코드 체크
            if (existCode != null) {
                return new ApiResponse<>(ApiResultCode.DUPLICATE_VALUE, String.format("%d 번째 줄, 이미 존재하는 값이 있습니다.", i));
            }

            codeRequestList.add(code);
        }

        try {
            // 코드_엑셀_업로드
            codeService.insertCodes(codeRequestList, jwtUser);
        } catch (Exception e) {
            return new ApiResponse<>(ApiResultCode.BAD_REQUEST, "데이터가 중복되거나 입력값이 유효하지 않습니다.");
        } finally {
            workbook.close();
        }

        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 코드관리 엑셀 템플릿 다운로드
     *
     * @param response
     * @param jwtUser
     */
    @AccessLog("코드관리 엑셀 템플릿 다운로드")
    @GetMapping(value = "/excel-template")
    @Operation(summary = "코드관리 엑셀 템플릿 다운로드")
    public void excelTemplate(
            HttpServletResponse response,
            @Parameter(hidden = true) @JwtUser User jwtUser
    ) {
        String excelTitle = "코드관리 엑셀 템플릿 다운로드";
        String[] columnArray = ExcelTitleCode.CODE_EXCEL_COLUMNS.getCode().split("\\|"); // | 로 split

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet worksheet = workbook.createSheet();
        CellStyle style = workbook.createCellStyle(); // 셀 스타일을 위한 변수
        style.setAlignment(HorizontalAlignment.CENTER); // 글 위치를 중앙으로 설정

        // 새로운 sheet를 생성한다.
        // 가장 첫번째 줄에 제목을 만든다.
        // 상위코드|코드|이름|영문명|설명|기타정보1|기타정보2|순서(Num)", "코드 관리 엑셀 일괄업로드
        XSSFRow row = worksheet.createRow(0);
        int columnIndex = 0;
        for (String colunmNm : columnArray) {
            row.createCell(columnIndex++).setCellValue(colunmNm);
        }

        CommUtil.makeSampletRow(columnArray, worksheet.createRow(1));

        // 사이즈 변경
        for (int x = 0; x < worksheet.getRow(0).getPhysicalNumberOfCells(); x++) {
            worksheet.autoSizeColumn(x);
            worksheet.setColumnWidth(x, (worksheet.getColumnWidth(x)) + 1200);
        }

        try {
            response.setContentType("application/vnd.ms-excel");
            String excelName = CommUtil.getFileName(excelTitle);
            response.setHeader("Content-Disposition", "attachement; filename=\"" + excelName + "\";charset=\"UTF-8\"");
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 코드관리_연관목록_조회 (페이징)
     * @param filter
     * @param pageRequest
     * @return
     */
    @AccessLog("코드관리 연관목록 조회")
    @GetMapping(value = "/related")
    @Operation(summary = "코드관리 연관목록 조회 (페이징)")
    public ApiResponse<PageResponse<CodeResponse.Paging>> selectRelatedCodePaging(
            @Parameter(name = "CodeRequestPagingFilter", description = "코드관리 연관목록 조회 (페이징) 필터", in = ParameterIn.PATH) CodeRequest.PagingFilter filter,
            @Parameter(name = "PageRequest", description = "코드관리 연관목록 조회 페이징") PageRequest<CodeRequest.PagingFilter> pageRequest
    ) {
        // 상위코드가 없을 시, default 값 셋팅
        if (StringUtils.isEmpty(filter.getCodeNm())) {
            filter.setCodeNm("도메인코드");
        }

        pageRequest.setFilter(filter);

        // 코드관리_목록_조회 (페이징)
        PageResponse<CodeResponse.Paging> response = codeService.selectRelatedCodePaging(pageRequest);

        return new ApiResponse<>(ApiResultCode.SUCCESS, response);
    }
}
