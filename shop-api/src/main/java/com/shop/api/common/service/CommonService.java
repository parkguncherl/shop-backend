package com.shop.api.common.service;

import com.shop.api.biz.system.service.UserService;
import com.shop.api.properties.GlobalProperties;
import com.shop.api.utils.ByteArrayMultipartFile;
import com.shop.core.biz.common.dao.FileDao;
import com.shop.core.biz.common.dao.GridDao;
import com.shop.core.biz.common.dao.SmsDao;
import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.biz.common.vo.response.CommonResponse;
import com.shop.core.biz.system.dao.UserDao;
import com.shop.core.entity.*;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.enums.FilePathType;
import com.shop.core.enums.GlobalConst;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.api.utils.CommUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    //private final SmsDao smsDao;
    private final FileDao fileDao;
    //private final UserDao userDao;
    //private final GridDao gridDao;
    private final FileService fileService;

    //private final GlobalProperties globalProperties;
    private final S3Presigner presigner;
    private final S3Client s3Client;


    public CommonService(
            UserService userService,
            //SmsDao smsDao,
            FileDao fileDao,
            //UserDao userDao,
            //GridDao gridDao,
            //GlobalProperties globalProperties,
            FileService fileService,
            @Qualifier("buildS3PresignerWithR2Specific") S3Presigner presigner,
            @Qualifier("buildS3ClientWithR2Specific") S3Client s3Client // 주입 과정에서의 불완전성을 제거하기 위해 어노테이션 기반 주입 대신 다음과 같은 명시적 생성자 선언
    ) {
        this.userService = userService;
        //this.smsDao = smsDao;
        this.fileDao = fileDao;
        //this.userDao = userDao;
        //this.gridDao = gridDao;
        //this.globalProperties = globalProperties;
        this.fileService = fileService;
        this.presigner = presigner;
        this.s3Client = s3Client;
    }

//    @Value("${aws.s3.bucketName.name}")
//    private String BUKET_NAME;

    @Value("${cloudflare.r2.bucketName.name}")
    private String BUKET_NAME;

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
    public FileDet uploadFile(MultipartFile file, String key, String originalFileName, String fileType, Integer fileId, Integer fileSeq, User jwtUser) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUKET_NAME)
                    .key(key)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            // 파일정보가 있는경우는 생략한다.
            if(fileId == null || fileId.compareTo(0) == 0) {
                FileMng fileMng = new FileMng();
                fileMng.setFileType(fileType);
                fileMng.setCreUser(jwtUser.getLoginId());
                fileMng.setUpdUser(jwtUser.getLoginId());
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
                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(BUKET_NAME)
                    .key(selectFile.getSysFileNm())
                    .build();

                s3Client.deleteObject(deleteObjectRequest);
                fileDao.deleteFileDetByUk(fileId, fileSeq, jwtUser);
            } catch (Exception e){
                throw new CustomRuntimeException(ApiResultCode.FAIL_CREATE, "s3 파일("+selectFile.getFileNm()+") 삭제 실패["+e.getMessage()+"]");
            }
        }
    }

    /* file 전체 삭제 */
    public Integer deleteAllFiles(Integer fileId, User jwtUser) {
        List<FileDet> files = fileDao.selectFileList(fileId);
        if(!files.isEmpty()) {
            for (FileDet fileDet : files) {
                this.deleteFile(fileDet.getFileId(), fileDet.getFileSeq(), jwtUser);
            }
        }
        return files.size(); // exception 없이 해당 코드까지 도달하였을 시 files.size() 에 해당하는 개수의 fileDet이 삭제되었으리라 여김이 마땅하므로 해당 값 반환하도록 함
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
             InputStream s3ObjectStream = s3Client.getObject(getObjectRequest)) {

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


    public String getFileUrl(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUKET_NAME)
                .key(fileName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(60*24*7)) // URL 유효 시간 설정 7주일간
                .build();

        return presigner.presignGetObject(presignRequest).url().toString();
    }

//    public Integer setUpGridColumn(GridRequest request, User jwtUser) {
//        User user = userService.selectUserById(jwtUser.getId());
//        if (ObjectUtils.isEmpty(user) && StringUtils.isEmpty(request.getUri()) && StringUtils.isEmpty(request.getSetValue())) {
//            throw new CustomRuntimeException(ApiResultCode.FAIL, "그리드 컬럼 저장에 필요한 입력값이 없습니다.");
//        }
//
//        request.setUserId(user.getId());
//        if(!StringUtils.equalsAny(request.getUri(),"/wms/info/ProductList")){ // 예외처리하는 컬럼들
//            return gridDao.upsertGridColumn(request);
//        } else {
//            return 1;
//        }
//    }
//
//    public Integer deleteGridColum(GridRequest request, User jwtUser) {
//        User user = userService.selectUserById(jwtUser.getId());
//        request.setUserId(user.getId());
//        return gridDao.deleteGridColum(request);
//    }
//
//    public GridResponse getGridColumn(String uri, User jwtUser) {
//        User user = userService.selectUserById(jwtUser.getId());
//        if (ObjectUtils.isEmpty(user) && StringUtils.isEmpty(uri)) {
//            throw new CustomRuntimeException(ApiResultCode.FAIL, "그리드 컬럼 조회 요청값이 없습니다.");
//        }
//
//        GridRequest request = new GridRequest();
//        request.setUserId(user.getId());
//        request.setUri(uri);
//        return gridDao.selectGridColum(request);
//    }

    /**
     * 단건파일 업로드
     * desc: 해당 영역은 전적으로 파일 업로드 요청에 따른 업로드(fileDet 추가 및 버킷에 오브젝트 저장 요청)만을 수행한다
     *
     * @param commonRequest
     * @return fileDown
     * */
    public CommonResponse.FileDown fileUpload(CommonRequest.FileUpload commonRequest, User jwtUser) throws IOException {
        Integer fileId = commonRequest.getFileId() == null ? 0 : commonRequest.getFileId();
        Integer fileSeq = fileService.selectMaxFileSeq(fileId);

        FileDet fileDet;

        if(commonRequest.getUploadFile() != null
                && StringUtils.isNotBlank(commonRequest.getImageFileHeight())
                && StringUtils.isNotBlank(commonRequest.getImageFileWidth())
                && StringUtils.isNumeric(commonRequest.getImageFileWidth())
                && StringUtils.isNumeric(commonRequest.getImageFileWidth())
        ) {
            // 이미지 파일로서 너비, 높이가 주어진 경우
            byte[] resizedImageBytes = this.resizeImageKeepAspectRatio(commonRequest.getUploadFile(), Integer.parseInt(commonRequest.getImageFileWidth()), Integer.parseInt(commonRequest.getImageFileHeight()));

            MultipartFile resizedFile = new ByteArrayMultipartFile(
                    resizedImageBytes,
                    commonRequest.getUploadFile().getOriginalFilename(),
                    commonRequest.getUploadFile().getContentType()
            );
            fileDet = this.fileUploadComm(jwtUser, resizedFile, fileId, fileSeq);
        } else {
            if (commonRequest.getUploadFile() == null) {
                throw new NullPointerException("업로드 대상 파일을 찾을 수 없음");
            }
            fileDet = this.fileUploadComm(jwtUser, commonRequest.getUploadFile(), fileId, fileSeq);
        }

        CommonResponse.FileDown fileDown = new CommonResponse.FileDown();
        fileDown.setFileNm(fileDet.getFileNm());
        fileDown.setFileId(fileDet.getFileId());
        fileDown.setSysFileNm(fileDet.getSysFileNm());
        fileDown.setFileSeq(fileDet.getFileSeq());

        return fileDown;
    }

    /**
     * 다수의 파일 업로드
     * desc: 해당 영역은 전적으로 다수 파일 업로드 요청에 따른 업로드(fileDet 추가 및 버킷에 오브젝트 저장 요청)만을 수행한다
     *
     * @param commonRequest
     * @return fileDowns
     * */
    public List<CommonResponse.FileDown> fileUploads(CommonRequest.FileUploads commonRequest, User jwtUser) throws IOException {
        List<CommonResponse.FileDown> fileDowns = new ArrayList<>();
        List<MultipartFile> fileList = commonRequest.getUploadFiles();
        Integer fileId = commonRequest.getFileId() == null ? 0 : commonRequest.getFileId();
        Integer fileSeq = 0;
        for (MultipartFile file : fileList) {
            if(fileId > 0){
                fileSeq = fileService.selectMaxFileSeq(fileId);
            } else {
                fileSeq++;
            }
            FileDet fileDet = this.fileUploadComm(jwtUser, file, fileId, fileSeq);
            fileId = fileDet.getFileId();
            CommonResponse.FileDown fileDown = new CommonResponse.FileDown();
            fileDown.setFileNm(fileDet.getFileNm());
            fileDown.setFileId(fileDet.getFileId());
            fileDown.setSysFileNm(fileDet.getSysFileNm());
            fileDown.setFileSeq(fileDet.getFileSeq());
            fileDowns.add(fileDown);
        }

        return fileDowns;
    }

    public FileDet fileUploadComm(User jwtUser, MultipartFile file, Integer fileId, Integer fileSeq) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String sysFileNm = GlobalConst.PRODUCT_CONTENTS_SHORT_NM.getCode() + "/" + UUID.randomUUID() + '.' + CommUtil.getFileExtension(originalFileName);
        return this.uploadFile(file, sysFileNm, originalFileName, FilePathType.PRODUCT_CONTENTS.getCode(), fileId, fileSeq, jwtUser);
    }

    /**
     * BufferedImage 리사이징 (고품질)
     */
    private BufferedImage resizeBufferedImage(BufferedImage originalImage, int width, int height) {
        // 고품질 리사이징을 위한 설정
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();

        // 고품질 렌더링 옵션 설정
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 이미지 그리기
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();

        return resizedImage;
    }
    /**
     * 비율 유지하면서 리사이징 (선택사항)
     */
    public byte[] resizeImageKeepAspectRatio(MultipartFile uploadFile, int width, int height) throws IOException {
        if (!isImageFile(uploadFile)) {
            throw new IllegalArgumentException("이미지 파일이 아닙니다: " + uploadFile.getContentType());
        }

        BufferedImage originalImage = ImageIO.read(uploadFile.getInputStream());
        if (originalImage == null) {
            throw new IOException("이미지를 읽을 수 없습니다.");
        }

        // 원본 이미지 크기
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // 비율 계산
        double aspectRatio = (double) originalWidth / originalHeight;

        int newWidth, newHeight;
        if (aspectRatio > 1) {
            // 가로가 더 긴 경우
            newWidth = width;
            newHeight = (int) (width / aspectRatio);
        } else {
            // 세로가 더 긴 경우
            newWidth = (int) (height * aspectRatio);
            newHeight = height;
        }

        // 250x150 캔버스에 중앙 정렬
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();

        // 배경색 설정 (흰색)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 고품질 렌더링 설정
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 중앙 정렬 좌표 계산
        int x = (width - newWidth) / 2;
        int y = (height - newHeight) / 2;

        // 이미지 그리기
        g2d.drawImage(originalImage, x, y, newWidth, newHeight, null);
        g2d.dispose();

        return bufferedImageToByteArray(resizedImage, getImageFormat(uploadFile));
    }

    /**
     * BufferedImage를 byte[]로 변환
     */
    private byte[] bufferedImageToByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }

    /**
     * 이미지 파일 여부 확인
     */
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    /**
     * 이미지 포맷 추출 (jpg, png, etc.)
     */
    private String getImageFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) return "jpg";

        switch (contentType) {
            case "image/png":
                return "png";
            case "image/gif":
                return "gif";
            case "image/bmp":
                return "bmp";
            case "image/jpeg":
            case "image/jpg":
            default:
                return "jpg";
        }
    }


    /**
     * 두 파일 간의 seq 교환(재정렬)
     *
     * @param fileRearrangementRequest
     * @param jwtUser
     * @return updatedRowsCnt(정상인 경우 2)
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer rearrangeFilesByStepsToMove(CommonRequest.FileRearrangementRequest fileRearrangementRequest, User jwtUser) {
        List<FileDet> selectFileDetList = fileDao.selectFileList(fileRearrangementRequest.getFileId());
        int selectFileDetListLen = selectFileDetList.size();

        int stepsCntToMove = fileRearrangementRequest.getStepsToMove();
        if (stepsCntToMove == 0) {
            // stepsCntToMove == 0 인 경우 동작이 무의미하므로 이 경우 즉시 반환처리
            return 0;
        }

        if (stepsCntToMove > 0) {
            // 아래쪽 방향 이동
            Integer updatedRowsCnt = 0;

            for (FileDet selectedFileDet : selectFileDetList) {
                FileDet fileDetForUpdate = new FileDet();
                fileDetForUpdate.setFileId(fileRearrangementRequest.getFileId());
                fileDetForUpdate.setUpdUser(jwtUser.getLoginId());

                int curSeq = selectedFileDet.getFileSeq();
                int targetSeq = -1;

                if (curSeq + stepsCntToMove > selectFileDetListLen) {
                    // seq 범위 초과, 초과 범위부터 seq 1에서부터 순차 할당
                    targetSeq = curSeq + stepsCntToMove - selectFileDetListLen; // 최초 초과 시점에 1, 그 이후부터는 순차 증가된 값 할당토록 보장
                } else {
                    // 그 외에는 기존 seq에서 이동
                    targetSeq = curSeq + stepsCntToMove;
                }

                if (targetSeq == -1) {
                    throw new CustomRuntimeException("fileSeq 갱신을 위한 값 할당 도중 문제 발생");
                }

                // 이하 fileDet 별 고유한 값
                fileDetForUpdate.setSysFileNm(selectedFileDet.getSysFileNm());
                fileDetForUpdate.setFileSeq(targetSeq);
                updatedRowsCnt += fileDao.updateFileDetBySysFileNm(fileDetForUpdate);
            }

            if (updatedRowsCnt != selectFileDetListLen) {
                throw new CustomRuntimeException("일부 행의 fileSeq가 갱신되지 않음");
            }
            return updatedRowsCnt;
        } else {
            // stepsCntToMove < 0
            // 위쪽 방향 이동
            Integer updatedRowsCnt = 0;

            for (FileDet selectedFileDet : selectFileDetList) {
                FileDet fileDetForUpdate = new FileDet();
                fileDetForUpdate.setFileId(fileRearrangementRequest.getFileId());
                fileDetForUpdate.setUpdUser(jwtUser.getLoginId());

                int curSeq = selectedFileDet.getFileSeq();
                int targetSeq = -1;

                if (curSeq + stepsCntToMove < 1) {
                    // seq 범위 하한 미달, 미달 범위부터 마지막 seq 에서부터 내림차순 순차 할당(이 시점에서 stepsCntToMove 는 음수이니 차이가 아닌 합을 기준으로 하한 미달 여부를 검증)
                    targetSeq = (curSeq + stepsCntToMove) + selectFileDetListLen; // 최초 미달 시점에 selectFileDetListLen(selectFileDetListLen - 0), 그 이후부터는 마지막 seq에서부터 내림차순 할당 보장
                } else {
                    // 그 외에는 기존 seq에서 이동
                    targetSeq = curSeq + stepsCntToMove;
                }

                if (targetSeq == -1) {
                    throw new CustomRuntimeException("fileSeq 갱신을 위한 값 할당 도중 문제 발생");
                }

                // 이하 fileDet 별 고유한 값
                fileDetForUpdate.setSysFileNm(selectedFileDet.getSysFileNm());
                fileDetForUpdate.setFileSeq(targetSeq);
                updatedRowsCnt += fileDao.updateFileDetBySysFileNm(fileDetForUpdate);
            }

            if (updatedRowsCnt != selectFileDetListLen) {
                throw new CustomRuntimeException("일부 행의 fileSeq가 갱신되지 않음");
            }
            return updatedRowsCnt;
        }
    }
}

