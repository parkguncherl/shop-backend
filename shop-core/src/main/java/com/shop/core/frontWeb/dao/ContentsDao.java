package com.shop.core.frontWeb.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.frontWeb.vo.request.ContentsRequest;
import com.shop.core.frontWeb.vo.response.ContentsResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * Description: Contents Dao
 * Date: 2026/05/12
 * Author: park junsung
 * </pre>
 */
@Mapper
@Repository
@RequiredArgsConstructor
public class ContentsDao {

    // MyBatis의 SqlSession을 주입받아 데이터베이스 작업을 수행합니다.
    private final SqlSession sqlSession;

    // MyBatis 매퍼 XML 파일의 네임스페이스를 정의합니다.
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.Contents.";

    /**
     * frontWeb 이하 컨텐츠 목록 조회(when code etc of partner_code is 'CONTENT')
     * @param pageRequest
     * @return ContentsInfo PageResponse
     */
    public PageResponse<ContentsResponse.ContentsInfo> selectContentsInfoListPaging(PageRequest<ContentsRequest.ContentsInfoListFilter> pageRequest) {
        List<ContentsResponse.ContentsInfo> contentsInfoList = sqlSession.selectList(NAMESPACE + "selectContentsInfoListPaging", pageRequest);
        if (contentsInfoList != null && !contentsInfoList.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), contentsInfoList, contentsInfoList.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }
}
