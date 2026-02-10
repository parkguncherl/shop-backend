package com.shop.api.common.service;

import com.shop.api.biz.system.service.UserService;
import com.shop.api.properties.GlobalProperties;
import com.shop.core.biz.common.dao.FileDao;
import com.shop.core.biz.common.dao.GridDao;
import com.shop.core.biz.common.dao.SmsDao;
import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.biz.common.vo.request.GridRequest;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.request.SmsRequest;
import com.shop.core.biz.common.vo.response.CommonResponse;
import com.shop.core.biz.common.vo.response.GridResponse;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.common.vo.response.SmsResponse;
import com.shop.core.biz.system.dao.UserDao;
import com.shop.core.entity.*;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.api.utils.CommUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <pre>
 * Description: 공통 Service
 * Date: 2024/06/17 11:52 AM
 * Company: smart90
 * Author: luckeey
 * </pre>
 */
@Slf4j
@Service
public class CommonService {

    private final UserService userService;
    private final SmsDao smsDao;
    private final FileDao fileDao;
    private final UserDao userDao;
    private final GridDao gridDao;

    private final GlobalProperties globalProperties;
//    private final S3Client s3Client;
//    private final S3Presigner presigner;

    private final S3Client CloudflareR2Client;

    public CommonService(
            UserService userService,
            SmsDao smsDao,
            FileDao fileDao,
            UserDao userDao,
            GridDao gridDao,
            GlobalProperties globalProperties,
            @Qualifier("cloudflareR2Client") S3Client CloudflareR2Client // 주입 과정에서의 불완전성을 제거하기 위해 어노테이션 기반 주입 대신 다음과 같은 명시적 생성자 선언
    ) {
        this.userService = userService;
        this.smsDao = smsDao;
        this.fileDao = fileDao;
        this.userDao = userDao;
        this.gridDao = gridDao;
        this.globalProperties = globalProperties;
        this.CloudflareR2Client = CloudflareR2Client;
    }

//    @Value("${aws.s3.bucketName.name}")
//    private String BUKET_NAME;

    @Value("${cloudflare.r2.bucketName.name}")
    private String BUKET_NAME;


    /**
     * 서버이벤트 POSTID별 SMS 페이징 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<SmsResponse.SvrEventPaging> selectSmsListPagingForSvrEvent(PageRequest<SmsRequest.SvrEventPagingFilter> pageRequest) {
        PageResponse<SmsResponse.SvrEventPaging> pageResponse = smsDao.selectSmsListPagingForSvrEvent(pageRequest);
        return pageResponse;
    }

    /**
     * 충전기_이벤트_SMS_이력_목록_조회 (페이징)
     *
     * @param pageRequest
     * @return
     */
    public PageResponse<SmsResponse.PostEventPaging> selectEventSmsHistoryPaging(PageRequest<SmsRequest.PostEventSmsHistoryPagingFilter> pageRequest) {
        return smsDao.selectEventSmsHistoryPaging(pageRequest);
    }

    /**
     * 파일_조회 (by Uk)
     *
     * @param file
     * @return
     */
    public CommonResponse.SelectFile selectFileDet(FileDet file) {
        return fileDao.selectFileDet(file.getFileId(), file.getFileSeq(), null);
    }

    /**
     * 파일_목록_조회 (by ID)
     *
     * @param fileId
     * @return
     */
    public List<FileDet> selectFileList(Integer fileId) {
        return fileDao.selectFileList(fileId);
    }

    /**
     * 파일_등록
     *
     * @param file
     * @return
     */
    public Integer insertFile(FileMng file) {
        return fileDao.insertFile(file);
    }


    /* file upload */
    public FileDet uploadFile(MultipartFile file, String key, String originalFileName, String filePath, Integer fileId, Integer fileSeq, User jwtUser) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUKET_NAME)
                    .key(key)
                    .build();

            CloudflareR2Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            // 파일정보가 있는경우는 생략한다.
            if(fileId == null || fileId.compareTo(0) == 0) {
                FileMng fileMng = new FileMng();
                fileMng.setFileType(filePath);
                fileDao.insertFile(fileMng);
                fileId = fileMng.getId();
            }
            FileDet fileDet = new FileDet();
            fileDet.setFileId(fileId);
            fileDet.setFileSeq(fileSeq);
            fileDet.setBucketName(BUKET_NAME);
            fileDet.setFileNm(originalFileName);
            fileDet.setSysFileNm(key);
            fileDet.setFileSize(new BigDecimal(file.getSize()).intValue());
            fileDet.setFileExt(CommUtil.getFileExtension(originalFileName));
            fileDet.setCreUser(jwtUser.getLoginId());
            fileDet.setUpdUser(jwtUser.getLoginId());
            fileDao.insertFileDet(fileDet);
            //String url = this.getFileUrl(BUKET_NAME, key);
            return fileDet;
        }
    }

    /* file 개별삭제 */
    public void deleteFile(Integer fileId, Integer fileSeq, User jwtUser) {
        CommonResponse.SelectFile selectFile = fileDao.selectFileDet(fileId, fileSeq, null);
        if(selectFile!= null && selectFile.getSysFileNm() != null) {
            try {
            /* 일단 원본파일은 삭제 하지 않는다.
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(BUKET_NAME)
                    .key(selectFile.getSysFileNm())
                    .build();

                s3Client.deleteObject(deleteObjectRequest);
             */
                fileDao.deleteFileDetByUk(fileId, fileSeq, jwtUser);
            } catch (Exception e){
                throw new CustomRuntimeException(ApiResultCode.FAIL_CREATE, "s3 파일("+selectFile.getFileNm()+") 삭제 실패["+e.getMessage()+"]");
            }
        }
    }

    /* file 전체 삭제 */
    public void deleteFiles(Integer fileId, User jwtUser) {
        List<FileDet> files = fileDao.selectFileList(fileId);
        if(!files.isEmpty()) {
            for (FileDet fileDet : files) {
                this.deleteFile(fileDet.getFileId(), fileDet.getFileSeq(), jwtUser);
            }
        }
    }

    public File downloadFile(String bucketName, String key) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        // 다운로드할 파일의 임시 저장 경로를 설정합니다.
        Path tempFilePath = Files.createTempFile("s3file-", ".tmp");
        File tempFile = tempFilePath.toFile();

        try (FileOutputStream fos = new FileOutputStream(tempFile);
             InputStream s3ObjectStream = CloudflareR2Client.getObject(getObjectRequest)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = s3ObjectStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }


    /**
     * 파일_삭제
     *
     * @param id
     * @return
     */
    public Integer deleteFile(Integer id, User jwtUser) {
        return fileDao.deleteFile(id, jwtUser);
    }

    /**
     * 파일_삭제
     *
     * @param id
     * @return
     */
    public Integer deleteFileDet(Integer id, User jwtUser) {
        return fileDao.deleteFileDet(id, jwtUser);
    }
    /**
     * 파일_삭제
     *
     * @param fileId
     * @param fileSeq
     * @return
     */
    public Integer deleteFileDetByUk(Integer fileId, Integer fileSeq, User jwtUser) {
        return fileDao.deleteFileDetByUk(fileId, fileSeq, jwtUser);
    }
    /**
     * 파일_삭제
     *
     * @param fileId
     * @return
     */
    public Integer deleteFileDetAll(Integer fileId, User jwtUser) {
        return fileDao.deleteFileDetAll(fileId, jwtUser);
    }
    

//    public String getFileUrl(String bucketName, String fileName) {
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(fileName)
//                .build();
//
//        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
//                .getObjectRequest(getObjectRequest)
//                .signatureDuration(Duration.ofMinutes(60*24*7)) // URL 유효 시간 설정 7주일간
//                .build();
//
//        return presigner.presignGetObject(presignRequest).url().toString();
//    }

    public Integer setUpGridColumn(GridRequest request, User jwtUser) {
        User user = userService.selectUserById(jwtUser.getId());
        if (ObjectUtils.isEmpty(user) && StringUtils.isEmpty(request.getUri()) && StringUtils.isEmpty(request.getSetValue())) {
            throw new CustomRuntimeException(ApiResultCode.FAIL, "그리드 컬럼 저장에 필요한 입력값이 없습니다.");
        }

        request.setUserId(user.getId());
        if(!StringUtils.equalsAny(request.getUri(),"/wms/info/ProductList")){ // 예외처리하는 컬럼들
            return gridDao.upsertGridColumn(request);
        } else {
            return 1;
        }
    }

    public Integer deleteGridColum(GridRequest request, User jwtUser) {
        User user = userService.selectUserById(jwtUser.getId());
        request.setUserId(user.getId());
        return gridDao.deleteGridColum(request);
    }

    public GridResponse getGridColumn(String uri, User jwtUser) {
        User user = userService.selectUserById(jwtUser.getId());
        if (ObjectUtils.isEmpty(user) && StringUtils.isEmpty(uri)) {
            throw new CustomRuntimeException(ApiResultCode.FAIL, "그리드 컬럼 조회 요청값이 없습니다.");
        }

        GridRequest request = new GridRequest();
        request.setUserId(user.getId());
        request.setUri(uri);
        return gridDao.selectGridColum(request);
    }

}

