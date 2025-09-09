package com.shop.api.biz.notice.service;

import com.shop.api.biz.system.service.UserService;
import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.Notice;
import com.shop.core.entity.User;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.enums.NoticeCd;
import com.shop.core.exception.CustomRuntimeException;
import com.shop.core.biz.notice.dao.NoticeDao;
import com.shop.core.biz.notice.vo.request.NoticeRequest;
import com.shop.core.biz.notice.vo.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 공지사항 서비스
 * 공지사항 관련 비즈니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeDao noticeDao;
    private final UserService userService;


    /**
     * 공지사항 목록을 조회합니다.
     *
     * @param request 페이지 요청
     * @param jwtUser 현재 로그인한 사용자 정보
     * @return 공지사항 목록
     */
    public PageResponse<NoticeResponse.Paging> getNoticeListPaging(PageRequest<NoticeRequest.PagingFilter> request, User jwtUser) {
        // 사용자 정보 설정 (필요시 사용자 정보로 필터링)
        return noticeDao.selectNoticeListPaging(request);
    }

    /**
     * 새로운 공지사항을 생성합니다.
     *
     * @param request 생성할 공지사항 정보
     * @param jwtUser 현재 로그인한 사용자 정보
     * @return 생성 성공 여부
     */
    @Transactional
    public Boolean createNotice(NoticeRequest.Create request, User jwtUser) {
        // 사용자 정보 설정
        request.setCreUser(jwtUser.getLoginId());
        request.setUpdUser(jwtUser.getLoginId());
        // DAO를 통해 공지사항 생성 및 결과 반환
        return noticeDao.insertNotice(request) > 0;
    }

    /**
     * 기존 공지사항을 수정합니다.
     *
     * @param request 수정할 공지사항 정보
     * @param jwtUser 현재 로그인한 사용자 정보
     * @return 수정 성공 여부
     */
    @Transactional
    public Boolean updateNotice(NoticeRequest.Update request, User jwtUser) {
        // 사용자 정보 설정
        request.setUpdUser(jwtUser.getLoginId());
        // DAO를 통해 공지사항 수정 및 결과 반환
        return noticeDao.updateNotice(request) > 0;
    }

    /**
     * 공지사항을 논리적으로 삭제합니다.
     *
     * @param listOfId 삭제할 공지사항 ID 목록
     * @param jwtUser 현재 로그인한 사용자 정보
     * @return 삭제된 공지사항 수
     */
    @Transactional
    public Integer deleteNotices(List<Integer> listOfId, User jwtUser) {
        int deletedNum = 0;
        for (Integer id : listOfId) {
            Notice notice = noticeDao.selectNoticeById(id);
            if (notice == null || "Y".equals(notice.getDeleteYn())) {
                throw new CustomRuntimeException(ApiResultCode.FAIL, "공지사항을 찾을 수 없거나 이미 삭제된 상태입니다.");
            }
            NoticeRequest.Delete deleteRequest = new NoticeRequest.Delete();
            deleteRequest.setId(id);
            deleteRequest.setUpdUser(jwtUser.getLoginId());
            if (noticeDao.deleteNotice(deleteRequest) != 1) {
                throw new CustomRuntimeException(ApiResultCode.FAIL, "공지사항 삭제 중 문제 발생");
            }
            deletedNum++;
        }
        return deletedNum;
    }

    /**
     * 공지사항 상세 정보를 조회하고 조회수를 증가시킵니다.
     * @param noticeId 조회할 공지사항 ID
     * @param jwtUser 현재 로그인한 사용자 정보
     * @return 공지사항 상세 정보
     */
    @Transactional
    public NoticeResponse.Detail getNoticeDetail(Integer noticeId, User jwtUser) {
        noticeDao.increaseReadCount(noticeId);
        Notice notice = noticeDao.selectNoticeById(noticeId);
        if (notice == null || "Y".equals(notice.getDeleteYn())) {
            throw new CustomRuntimeException(ApiResultCode.FAIL, "공지사항 정보를 찾을 수 없습니다.");
        }
        return NoticeResponse.Detail.fromEntity(notice);
    }

    /**
     * 공지사항의 내용을 null로 변경합니다.
     * @param noticeId 내용을 삭제할 공지사항 ID
     * @param jwtUser 현재 로그인한 사용자 정보
     * @return 변경 성공 여부
     */
    @Transactional
    public Boolean removeNoticeContent(Integer noticeId, User jwtUser) {
        // 공지사항이 존재하는지 먼저 확인합니다.
        Notice notice = noticeDao.selectNoticeById(noticeId);
        if (notice == null || "Y".equals(notice.getDeleteYn())) {
            throw new CustomRuntimeException(ApiResultCode.FAIL, "공지사항 정보를 찾을 수 없습니다.");
        }
        // 공지사항 내용을 null로 변경합니다.
        return noticeDao.updateNoticeContentToNull(noticeId) > 0;
    }
    /**
     * 파트너 코드와 일치하는 가장 낮은 ID의 스티커 정보를 조회합니다.
     *
     * @param jwtUser
     * @return 스티커 정보
     */
    public NoticeResponse.Sticker getStickerInfo(User jwtUser) {
        User user = userService.selectUserById(jwtUser.getId());
        return noticeDao.selectStickerInfo(user.getPartnerId(), NoticeCd.NOTICE.getCode());
    }

    @Transactional
    public Boolean updateStickerInfo(NoticeRequest.StickerUpdate request) {
        return noticeDao.updateStickerInfo(request) > 0;
    }
}
