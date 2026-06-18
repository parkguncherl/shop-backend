package com.shop.core.frontWeb.dao;

import com.shop.core.entity.Comu;
import com.shop.core.entity.ComuDet;
import com.shop.core.frontWeb.vo.response.ComuResponse;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ComuDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NS = "com.shop.mapper.frontWeb.Comu.";

    public int insertComu(Comu comu) {
        return sqlSession.insert(NS + "insertComu", comu);
    }

    public Comu selectComuById(Long id) {
        return sqlSession.selectOne(NS + "selectComuById", id);
    }

    public List<ComuResponse.Summary> selectComuListByOrderId(Long orderId) {
        return sqlSession.selectList(NS + "selectComuListByOrderId", orderId);
    }

    public int insertComuDet(ComuDet comuDet) {
        return sqlSession.insert(NS + "insertComuDet", comuDet);
    }

    public ComuDet selectComuDetById(Long id) {
        return sqlSession.selectOne(NS + "selectComuDetById", id);
    }

    public List<ComuResponse.Message> selectMessagesByComuId(Long comuId) {
        return sqlSession.selectList(NS + "selectMessagesByComuId", comuId);
    }

    public int deleteComuDet(Long id) {
        return sqlSession.update(NS + "deleteComuDet", id);
    }

    public int deleteComu(Long id) {
        return sqlSession.update(NS + "deleteComu", id);
    }

    public int deleteComuDetByComuId(Long comuId) {
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("comuId", comuId);
        return sqlSession.update(NS + "deleteComuDetByComuId", params);
    }

    public String selectCodeDesc(String codeUpper, String codeCd) {
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("codeUpper", codeUpper);
        params.put("codeCd", codeCd);
        return sqlSession.selectOne(NS + "selectCodeDesc", params);
    }

    public String selectCodeNm(String codeUpper, String codeCd) {
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("codeUpper", codeUpper);
        params.put("codeCd", codeCd);
        return sqlSession.selectOne(NS + "selectCodeNm", params);
    }
}
