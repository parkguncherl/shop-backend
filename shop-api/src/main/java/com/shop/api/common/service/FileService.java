package com.shop.api.common.service;

import com.shop.core.biz.common.dao.FileDao;
import com.shop.core.entity.FileDet;
import com.shop.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final Logger log = LoggerFactory.getLogger(FileService.class);
    private final FileDao fileDao;

    public String getFileContentAsBase64(Integer fileId) {

        return null; // 임시 반환값, 실제 구현 필요
    }

    public Integer selectMaxFileSeq(Integer fileId) {
        return fileDao.selectMaxFileSeq(fileId);
    }

    /**
     * 파일 수정
     * @param request
     * @return
     */
    public Integer updateFileDet(FileDet request) {
        return fileDao.updateFileDet(request);
    }

    /**
     * 파일 수정 (복수)
     * @param requestList
     * @return
     */
    public Integer updateFileDets(List<FileDet> requestList) {
        // 삽입된 행의 수를 집계할 변수
        int updateCount = 0;
        for (FileDet item : requestList) {
            Integer result = fileDao.updateFileDet(item);
            if (result != null) {
                updateCount++;
            }
        }
        return updateCount;
    }

    /**
     * 파일 삭제 ( file테이블 )
     * @param fileId
     * @return
     */
    public Integer deleteFile(Integer fileId, User jwtUser) {
        return fileDao.deleteFile(fileId, jwtUser);
    }
}