package com.shop.api.product.service;

import com.shop.api.biz.system.service.UserService;
import com.shop.api.common.service.CommonService;
import com.shop.core.biz.common.vo.request.CommonRequest;
import com.shop.core.entity.FileDet;
import com.shop.core.entity.User;
import com.shop.api.utils.CommUtil;
import com.shop.core.enums.FilePathType;
import com.shop.core.product.dao.ProductContentsDao;
import com.shop.core.product.vo.request.ProductContentsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.shop.core.enums.GlobalConst;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <pre>
 * Description: ProductContents Service
 * Date: 2026/02/04
 * Author: park junsung
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductContentsService {

    private final ProductContentsDao productContentsDao;
    private final CommonService commonService;
    private final UserService userService;

    /**
     * 신규 상품컨텐츠 데이터 추가
     * @param insertProductContents
     * @return 추가된 행의 수
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer insertProductContents(ProductContentsRequest.InsertProductContents insertProductContents, User jwtUser) throws IOException {
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

        insertProductContents.setPartnerId(userService.selectPartnerIdByLoginId(jwtUser.getLoginId()));
        insertProductContents.setNewsType(GlobalConst.PRODUCT_CONTENTS_NEWS_TYPE.getCode());

        insertProductContents.setCreUser(jwtUser.getLoginId());
        insertProductContents.setUpdUser(jwtUser.getLoginId());
        return productContentsDao.insertProductContents(insertProductContents);
    }
}
