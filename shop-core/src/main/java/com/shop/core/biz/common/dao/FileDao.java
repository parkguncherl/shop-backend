package com.shop.core.biz.common.dao;

import com.shop.core.biz.common.vo.response.CommonResponse;
import com.shop.core.entity.FileMng;
import com.shop.core.entity.FileDet;
import com.shop.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Description: 파일 Dao
 * Date: 2023/04/13 15:39 PM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Repository
@RequiredArgsConstructor
public class FileDao {

    private final String PRE_NS = "com.shop.mapper.file.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;

    /**
     * 파일_조회 (by Uk)
     *
     * @param fileId
     * @param fileSeq
     * @return
     */
    public CommonResponse.SelectFile selectFileDet(Integer fileId, Integer fileSeq, String key) {
        Map<String, Object> params = new HashMap<>();
        CommonResponse.SelectFile selectFile = new CommonResponse.SelectFile();
        params.put("fileId", fileId);
        params.put("fileSeq", fileSeq);
        params.put("key", key);
        FileDet fileDet = sqlSession.selectOne(PRE_NS.concat("selectFileDet"), params);
        selectFile.setFileId(fileId);
        selectFile.setFileSeq(fileSeq);
        selectFile.setFileNm(fileDet.getFileNm());
        selectFile.setSysFileNm(fileDet.getSysFileNm());
        selectFile.setFileSize(fileDet.getFileSize());
        selectFile.setFileExt(fileDet.getFileExt());
        selectFile.setBucketName(fileDet.getBucketName());
        return selectFile;
    }

    /**
     * 파일_목록_조회 (by ID)
     *
     * @param fileId
     * @return
     */
    public List<FileDet> selectFileList(Integer fileId) {
        return sqlSession.selectList(PRE_NS.concat("selectFileList"), fileId);
    }

    /**
     * 파일_등록
     *
     * @param file
     * @return
     */
    public Integer insertFile(FileMng file) {
        return sqlSession.insert(PRE_NS.concat("insertFile"), file);
    }

    /**
     * 파일_등록
     *
     * @param fileDet
     * @return
     */
    public Integer insertFileDet(FileDet fileDet) {
        return sqlSession.insert(PRE_NS.concat("insertFileDet"), fileDet);
    }


    /**
     * 파일_seq 조회
     *
     * @param fileId
     * @return
     */
    public Integer selectMaxFileSeq(Integer fileId) {
        return sqlSession.selectOne(PRE_NS.concat("selectMaxFileSeq"), fileId);
    }

    /**
     * 파일 수정
     * @param request
     * @return
     */
    public Integer updateFileDet(FileDet request) {
        return sqlSession.insert(PRE_NS.concat("updateFileDet"), request);
    }

    /**
     * 파일_삭제 (개별)
     *
     * @param id
     * @return
     */
    public Integer deleteFile(Integer id, User jwtUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("updUser", jwtUser.getLoginId());
        return sqlSession.update(PRE_NS.concat("deleteFile"), params);
    }

    /**
     * 파일_삭제 (개별)
     *
     * @param id
     * @return
     */
    public Integer deleteFileDet(Integer id, User jwtUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("updUser", jwtUser.getLoginId());
        return sqlSession.update(PRE_NS.concat("deleteFileDet"), params);
    }

    /**
     * 파일_삭제 (개별)
     *
     * @param fileId
     * @param fileSeq
     * @param jwtUser
     * @return
     */
    public Integer deleteFileDetByUk(Integer fileId, Integer fileSeq, User jwtUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileId", fileId);
        params.put("fileSeq", fileSeq);
        params.put("updUser", jwtUser.getLoginId());
        return sqlSession.update(PRE_NS.concat("deleteFileDetByUk"), params);
    }

    /**
     * 파일_삭제 (개별)
     *
     * @param fileId
     * @param sysFileNm
     * @param jwtUser
     * @return
     */
    public Integer deleteFileDetByKey(Integer fileId, String sysFileNm, User jwtUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileId", fileId);
        params.put("sysFileNm", sysFileNm);
        params.put("updUser", jwtUser.getLoginId());
        return sqlSession.update(PRE_NS.concat("deleteFileDetByKey"), params);
    }

    /**
     * 파일_삭제 (전체)
     *
     * @param fileId
     * @param jwtUser
     * @return
     */
    public Integer deleteFileDetAll(Integer fileId, User jwtUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileId", fileId);
        params.put("updUser", jwtUser.getLoginId());
        return sqlSession.update(PRE_NS.concat("deleteFileDetAll"), params);
    }

}
