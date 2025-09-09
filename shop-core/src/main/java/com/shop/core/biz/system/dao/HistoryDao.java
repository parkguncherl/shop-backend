package com.shop.core.biz.system.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.system.vo.request.ContactRequest;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.biz.system.vo.response.ContactResponse;
import com.shop.core.entity.Contact;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.exception.CustomRuntimeException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * Description: 접속로그_관리 Dao
 * Date: 2023/02/14 11:58 AM
 * Company: smart90
 * Author : luckeey
 * </pre>
 */
@Repository
@RequiredArgsConstructor
public class HistoryDao {

    private final String PRE_NS = "com.binblur.mapper.history.";

    @Qualifier("sqlSessionTemplate")
    private final SqlSession sqlSession;


    /**
     * 이력생성
     * @param tableName
     * @param befId
     * @return
     */
    public Integer insertHistory(String tableName, Integer befId) {
        List<String> columns = sqlSession.selectList(PRE_NS.concat("selectColumns"), tableName);
        Map<String, Object> params = new HashMap<>();
        params.put("tableName", tableName);
        params.put("befId", befId);
        params.put("columns", columns);
        sqlSession.insert(PRE_NS.concat("insertHistory"), params);
        return  (Integer) params.get("id"); // 자동 생성된 ID 가져오기
    }


    /**
     * 이력생성후 변경건 비교
     * 변경된게 없으면 이력을 삭제한다.
     * @param tableName
     * @param befId
     * @return
     */
    public void checkChangeData(String tableName, Integer befId, Integer insId) {
        Map<String, Object> params = new HashMap<>();
        params.put("tableName", tableName);
        params.put("befId", befId);
        List<String> columns;
        String importantColumn = sqlSession.selectOne(PRE_NS.concat("getImportantColumn"), tableName); // 중요컬럼이 등록되어 있으면
        if(StringUtils.isNotEmpty(importantColumn)){
            columns = Arrays.stream(importantColumn.split(","))
                    .filter(column -> !StringUtils.isBlank(column)) // NULL 및 빈 문자열 제외
                    .collect(Collectors.toList());
            params.put("columns", columns);
            int count = sqlSession.selectOne(PRE_NS.concat("checkChangeData"), params);
            if(count == 1){
                //생성이력을 삭제한다.
                params.put("insId", insId);
                sqlSession.delete(PRE_NS.concat("deleteHistory"), params);
            }

        } else {
            columns = sqlSession.selectList(PRE_NS.concat("selectColumnsForSame"), tableName);
            params.put("columns", columns);
            int count = sqlSession.selectOne(PRE_NS.concat("checkChangeData"), params);
            if(count == 1){
                throw new CustomRuntimeException(ApiResultCode.FAIL, "변경내역이 존재하지 않습니다.");
            }
        }
    }

    
}
