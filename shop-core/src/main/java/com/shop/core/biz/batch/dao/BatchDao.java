package com.shop.core.biz.batch.dao;

import com.shop.core.entity.Asn;
import com.shop.core.entity.Batch;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
@RequiredArgsConstructor
public class BatchDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.shop.mapper.batch.";

    public List<Asn> selectAsnForRemainStock() {
        return sqlSession.selectList(NAMESPACE + "selectAsnForRemainStock");
    }

    public Integer selectAsnCountByParentId(Integer parentId) {
        return sqlSession.selectOne(NAMESPACE + "selectAsnCountByParentId", parentId);
    }

    /**
     * 발주생성
     * @param asnId
     */
    public Integer insertAsnForBatch(Integer asnId) {
        List<String> columns = sqlSession.selectList(NAMESPACE.concat("selectAsnColumns"));
        Map<String, Object> params = new HashMap<>();
        params.put("columns", columns);
        params.put("asnId", asnId);
        return sqlSession.insert(NAMESPACE + "insertAsnForBatch", params);
    }

    /**
     * 일괄 발주생성 (TODO. 현재는 미사용으로 추후 사용계획이 없을때는 삭제할것)
     */
    public Integer insertAsnForRemainStock() {
        List<String> columns = sqlSession.selectList(NAMESPACE.concat("selectAsnColumns"));
        Map<String, Object> params = new HashMap<>();
        params.put("columns", columns);
        return sqlSession.insert(NAMESPACE + "insertAsnForRemainStock", params);
    }

    /**
     * 배치 로그 생성
     */
    public Integer insertBatchLog(Batch batch) {
        return sqlSession.insert(NAMESPACE + "insertBatchLog", batch);
    }
}
