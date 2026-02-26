package com.shop.api.product.service;

import com.shop.api.biz.system.service.UserService;
import com.shop.api.common.service.CommonService;
import com.shop.api.utils.CommUtil;
import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.FileDet;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.enums.FilePathType;
import com.shop.core.enums.GlobalConst;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.core.product.dao.ProductContentListDao;
import com.shop.core.product.dao.ProductContentsDao;
import com.shop.core.product.dao.ProductMngDao;
import com.shop.core.product.vo.request.ProductContentListRequest;
import com.shop.core.product.vo.request.ProductContentsRequest;
import com.shop.core.product.vo.request.ProductMngRequest;
import com.shop.core.product.vo.response.ProductContentListResponse;
import com.shop.core.product.vo.response.ProductMngResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <pre>
 * Description: ProductContentList Service
 * Date: 2026/02/13
 * Author: park junsung
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductContentListService {

    private final UserService userService;
    private final ProductContentListDao productContentListDao;
    private final CommonService commonService;

    /**
     * 상품관리-상품컨텐츠목록 조회
     * @param pageRequest
     * @return 페이징된 ProductContent List
     */
    public PageResponse<ProductContentListResponse.ProductContent> selectProdContentList(PageRequest<ProductContentListRequest.ProductContentListFilter> pageRequest, User jwtUser) {
        pageRequest.getFilter().setPartnerId(userService.selectPartnerIdByLoginId(jwtUser.getLoginId()));
        pageRequest.getFilter().setNewsType(GlobalConst.PRODUCT_CONTENTS_NEWS_TYPE.getCode());
        return productContentListDao.selectProdContentList(pageRequest);
    }

    /**
     * 신규 상품컨텐츠 데이터 추가
     * @param insertProductContents
     * @return 추가된 행의 수
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer insertProductContents(ProductContentListRequest.InsertProductContents insertProductContents, User jwtUser) throws IOException {
        if (insertProductContents.getCommonRequestFileUploads() != null) {
            // 파일 업로드 요청이 포함된 경우 이에 대응하는 로직
            CommonRequest.FileUploads fileUploads = insertProductContents.getCommonRequestFileUploads();
            List<MultipartFile> fileList = fileUploads.getUploadFiles();
            Integer fileId = 0;
            Integer fileSeq = 0;
            for (MultipartFile file : fileList) {
                fileSeq++;
                String originalFileName = file.getOriginalFilename();
                String sysFileNm = GlobalConst.PRODUCT_CONTENTS_SHORT_NM.getCode() + "/" + UUID.randomUUID() + '.' + CommUtil.getFileExtension(originalFileName);

                FileDet fileDet = commonService.uploadFile(file, sysFileNm, originalFileName, FilePathType.PRODUCT_CONTENTS.getCode(), fileId, fileSeq, jwtUser);

                if (fileId == 0) {
                    fileId = fileDet.getFileId(); // 최초 한정으로 업로딩 결과 반환된 id를 할당하여 이후 반복 구문에서 사용 가능하도록 함
                    insertProductContents.setFileId(fileDet.getFileId()); // 파일 id 할당하여 해당 상품 컨텐츠와의 관계 정의
                }
            }
        }

        // 이하 contents 테이블 데이터 추가를 위한 영역
        insertProductContents.setPartnerId(userService.selectPartnerIdByLoginId(jwtUser.getLoginId()));
        insertProductContents.setNewsType(GlobalConst.PRODUCT_CONTENTS_NEWS_TYPE.getCode());

        insertProductContents.setCreUser(jwtUser.getLoginId());
        insertProductContents.setUpdUser(jwtUser.getLoginId());
        return productContentListDao.insertProductContents(insertProductContents);
    }

    /**
     * 단일 Contents 데이터 및 연관된 상품정보를 삭제
     *
     * @param deleteProductContents
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteProductContents(ProductContentListRequest.DeleteProductContents deleteProductContents, User jwtUser) throws IOException {
        if (deleteProductContents.getFileId() != null) {
            // 연관된 (이미지)파일 삭제 영역
            List<FileDet> fileList = commonService.selectFileList(deleteProductContents.getFileId());
            Integer deletedFileCnt = commonService.deleteAllFiles(deleteProductContents.getFileId(), jwtUser);
            Integer deletedFileInfoCnt = commonService.deleteFile(deleteProductContents.getFileId(), jwtUser); // file (데이터) 삭제
            if (deletedFileCnt.compareTo(fileList.size()) != 0) {
                // 불일치 시 예외
                throw new IOException("삭제되어야 할 fileDet 과 실제로 그리 된 fileDet의 개수가 다름");
            } else if (deletedFileInfoCnt != 1) {
                throw new IOException("file 데이터가 삭제되지 않음");
            }
        }

        // 이하 contents 테이블 데이터 삭제를 위한 영역
        deleteProductContents.setUpdUser(jwtUser.getLoginId());
        Integer deletedRowCnt = productContentListDao.deleteProductContents(deleteProductContents);
        if (!deletedRowCnt.equals(1)) {
            throw new CustomRuntimeException(ApiResultCode.FAIL_CREATE, "정보 삭제 중 문제 발생, 점검!");
        }
    }
}
