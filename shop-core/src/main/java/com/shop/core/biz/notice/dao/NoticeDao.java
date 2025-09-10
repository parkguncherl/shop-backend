package com.shop.core.biz.notice.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.biz.notice.vo.request.NoticeRequest;
import com.shop.core.biz.notice.vo.response.NoticeResponse;
import com.shop.core.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class NoticeDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.notice.";

    /**
     * 공지사항 목록(페이징)을 조회합니다.
     * @param pageRequest 페이지 요청 정보
     * @return 공지사항 목록
     */
    public PageResponse<NoticeResponse.Paging> selectNoticeListPaging(PageRequest<NoticeRequest.PagingFilter> pageRequest) {
        List<NoticeResponse.Paging> notices = sqlSession.selectList(NAMESPACE.concat("selectNoticeListPaging"), pageRequest);
        // 총 개수를 별도로 조회하지 않고, 쿼리 결과에서 가져옵니다.
        int totalCount = notices.isEmpty() ? 0 : notices.get(0).getTotalRowCount();
        return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), notices, totalCount);
    }
    /**
     * 새로운 공지사항을 생성합니다.
     * @param request 생성할 공지사항 정보
     * @return 영향받은 행의 수
     */
    public int insertNotice(NoticeRequest.Create request) {
        return sqlSession.insert(NAMESPACE.concat("insertNotice"), request);
    }

    /**
     * 기존 공지사항을 수정합니다.
     * @param request 수정할 공지사항 정보
     * @return 영향받은 행의 수
     */
    public Integer updateNotice(NoticeRequest.Update request) {
        return sqlSession.update(NAMESPACE.concat("updateNotice"), request);
    }

    /**
     * 공지사항을 논리적으로 삭제합니다.
     * @param request 삭제할 공지사항 정보
     * @return 영향받은 행의 수
     */
    public Integer deleteNotice(NoticeRequest.Delete request) {
        return sqlSession.update(NAMESPACE.concat("deleteNotice"), request);
    }
    /**
     * 공지사항을 조회하고 조회수를 증가시킵니다.
     * @param noticeId 조회할 공지사항 ID
     * @return 조회된 공지사항 정보
     */
    public void increaseReadCount(Integer noticeId) {
        sqlSession.update(NAMESPACE.concat("increaseReadCount"), noticeId);
    }

    public Notice selectNoticeById(Integer noticeId) {
        return sqlSession.selectOne(NAMESPACE.concat("selectNoticeById"), noticeId);
    }

    /**
     * 공지사항의 내용을 null로 변경합니다.
     * @param noticeId 내용을 삭제할 공지사항 ID
     * @return 영향받은 행의 수
     */
    public int updateNoticeContentToNull(Integer noticeId) {
        return sqlSession.update(NAMESPACE.concat("updateNoticeContentToNull"), noticeId);
    }
    /**
     * 파트너 코드와 일치하는 가장 낮은 ID의 스티커 정보를 조회합니다.
     *
     * @param partnerId 파트너 코드 noticeCd
     * @return 스티커 정보
     */
    public NoticeResponse.Sticker selectStickerInfo(Integer partnerId, String noticeCd) {
        Map<String, Object> params = new HashMap<>();
        params.put("partnerId", partnerId);
        params.put("noticeCd", noticeCd);
        return sqlSession.selectOne(NAMESPACE.concat("selectStickerInfo"), params);
    }

    /**
     * 파트너 코드와 일치하는 가장 낮은 ID의 스티커 정보를 수정합니다.
     *
     * @param request 수정할 스티커 정보
     * @return 수정된 행의 수
     */
    public int updateStickerInfo(NoticeRequest.StickerUpdate request) {
        return sqlSession.update(NAMESPACE.concat("updateStickerInfo"), request);
    }
}
