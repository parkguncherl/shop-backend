package com.binblur.core.biz.common.dao;

import com.binblur.core.biz.common.vo.response.CommonResponse;
import com.binblur.core.entity.FileMng;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description : 파일 Dao
 * Date : 2023/04/13 15:39 PM
 * Company : smart90
 * Author : sclee9946
 * </pre>
 */
@Repository
public class FileDao {

    private final String PRE_NS = "com.binblur.mapper.fileMng.";

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSession sqlSession;

    /**
     * 파일_조회 (by Uk)
     *
     * @param file
     * @return
     */
    public CommonResponse.SelectFile selectFileByUk(FileMng file) {
        return sqlSession.selectOne(PRE_NS.concat("selectFileByUk"), file);
    }

    /**
     * 파일_목록_조회 (by ID)
     *
     * @param fileId
     * @return
     */
    public List<CommonResponse.SelectFiles> selectFileList(Integer fileId) {
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
     * 파일_삭제 (개별)
     *
     * @param file
     * @return
     */
    public Integer deleteFile(FileMng file) {
        return sqlSession.update(PRE_NS.concat("deleteFile"), file);
    }
}
