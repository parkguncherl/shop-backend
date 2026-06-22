package com.shop.api.frontWeb.service;

import com.shop.core.biz.partner.dao.PartnerDao;
import com.shop.core.biz.partner.vo.response.PartnerResponse;
import com.shop.core.entity.Comu;
import com.shop.core.entity.ComuDet;
import com.shop.core.entity.SocialAccount;
import com.shop.core.frontWeb.dao.ComuDao;
import com.shop.core.frontWeb.dao.SocialAccountDao;
import com.shop.core.frontWeb.vo.request.ComuRequest;
import com.shop.core.frontWeb.vo.response.ComuResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ComuService {

    private final ComuDao comuDao;
    private final SocialAccountDao socialAccountDao;
    private final PartnerDao partnerDao;

    private static final Integer DEFAULT_PARTNER_ID = 1;

    private static final String COMU_CODE_UPPER   = "10130";
    private static final String ADMIN_USER        = "system-bot";
    private static final String DEFAULT_GREETING_MSG = "안녕하세요 고객님";

    @Transactional
    public ComuResponse.Thread createComu(ComuRequest.Create request) {
        SocialAccount account = socialAccountDao.selectById(request.getSocialAccountId());
        if (account == null) {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }

        String displayName = resolveDisplayName(account);

        // 상담 헤더 생성
        Comu comu = Comu.builder()
                .comuType(request.getComuType())
                .eventId(request.getOrderId())
                .creUser(displayName)
                .build();
        comuDao.insertComu(comu);

        // 자동 메시지 1: TB_PARTNER.first_greeting_message
        PartnerResponse.Select partner = partnerDao.selectPartnerDet(DEFAULT_PARTNER_ID);
        String greetingMsg = (partner != null && StringUtils.isNotBlank(partner.getFirstGreetingMessage()))
                ? partner.getFirstGreetingMessage()
                : DEFAULT_GREETING_MSG;

        comuDao.insertComuDet(ComuDet.builder()
                .comuId(comu.getId())
                .reqYn("N")
                .comuCntn(greetingMsg)
                .creUser(ADMIN_USER)
                .build());

        // 자동 메시지 2: 유형별 안내문구 - code_desc
        String codeDesc = comuDao.selectCodeDesc(COMU_CODE_UPPER, request.getComuType());
        if (StringUtils.isNotBlank(codeDesc)) {
            comuDao.insertComuDet(ComuDet.builder()
                    .comuId(comu.getId())
                    .reqYn("N")
                    .comuCntn(codeDesc)
                    .creUser(ADMIN_USER)
                    .build());
        }

        return getThread(comu.getId());
    }

    @Transactional
    public ComuResponse.Thread addMessage(Long comuId, ComuRequest.AddMessage request) {
        Comu comu = comuDao.selectComuById(comuId);
        if (comu == null) {
            throw new IllegalArgumentException("상담 정보를 찾을 수 없습니다.");
        }

        SocialAccount account = socialAccountDao.selectById(request.getSocialAccountId());
        if (account == null) {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }

        ComuDet det = ComuDet.builder()
                .comuId(comuId)
                .reqYn("Y")
                .comuCntn(request.getContent())
                .fileId(request.getFileId())
                .creUser(resolveDisplayName(account))
                .build();
        comuDao.insertComuDet(det);

        return getThread(comuId);
    }

    @Transactional
    public void deleteComu(Long comuId) {
        comuDao.deleteComuDetByComuId(comuId);
        comuDao.deleteComu(comuId);
    }

    @Transactional
    public void deleteMessage(Long comuDetId, Long socialAccountId) {
        ComuDet det = comuDao.selectComuDetById(comuDetId);
        if (det == null) {
            throw new IllegalArgumentException("메시지를 찾을 수 없습니다.");
        }
        if (!"Y".equals(det.getReqYn())) {
            throw new IllegalStateException("고객 메시지만 삭제할 수 있습니다.");
        }

        // 작성자 확인: creUser 로 직접 비교 대신 socialAccountId → displayName 검증
        SocialAccount account = socialAccountDao.selectById(socialAccountId);
        if (account == null || !resolveDisplayName(account).equals(det.getCreUser())) {
            throw new IllegalStateException("본인 메시지만 삭제할 수 있습니다.");
        }

        comuDao.deleteComuDet(comuDetId);
    }

    public ComuResponse.Thread getThread(Long comuId) {
        Comu comu = comuDao.selectComuById(comuId);
        if (comu == null) {
            throw new IllegalArgumentException("상담 정보를 찾을 수 없습니다.");
        }
        List<ComuResponse.Message> messages = comuDao.selectMessagesByComuId(comuId);

        ComuResponse.Thread thread = new ComuResponse.Thread();
        thread.setId(comu.getId());
        thread.setComuType(comu.getComuType());
        thread.setComuTypeName(comuDao.selectCodeNm(COMU_CODE_UPPER, comu.getComuType()));
        thread.setOrderId(comu.getEventId());
        thread.setCreTm(comu.getCreTm());
        thread.setMessages(messages);
        return thread;
    }

    public List<ComuResponse.BoListItem> getComuListForBo(String comuType, String paymentStatus,
                                                          String productName,
                                                          LocalDate fromDate, LocalDate toDate) {
        Map<String, Object> params = new HashMap<>();
        if (comuType != null && !comuType.isBlank()) params.put("comuType", comuType);
        if (paymentStatus != null && !paymentStatus.isBlank()) params.put("paymentStatus", paymentStatus);
        if (productName != null && !productName.isBlank()) params.put("productName", productName);
        if (fromDate != null) params.put("fromDate", fromDate.atStartOfDay());
        if (toDate != null) params.put("toDate", toDate.plusDays(1).atStartOfDay());
        return comuDao.selectComuListForBo(params);
    }

    @Transactional
    public ComuResponse.Thread addAdminReply(Long comuId, String content, Integer fileId) {
        Comu comu = comuDao.selectComuById(comuId);
        if (comu == null) throw new IllegalArgumentException("상담 정보를 찾을 수 없습니다.");

        comuDao.insertComuDet(ComuDet.builder()
                .comuId(comuId)
                .reqYn("N")
                .comuCntn(content)
                .fileId(fileId)
                .creUser(ADMIN_USER)
                .build());

        return getThread(comuId);
    }

    @Transactional
    public void markRead(Long comuId, boolean callerIsAdmin) {
        // 관리자가 읽으면 고객 메시지(reqYn='Y')를 읽음 처리
        // 고객이 읽으면 관리자 메시지(reqYn='N')를 읽음 처리
        String reqYnToMark = callerIsAdmin ? "Y" : "N";
        comuDao.updateReadYnByComuId(comuId, reqYnToMark);
    }

    public List<ComuResponse.ProductQna> getProductQnaList(Long productId) {
        return comuDao.selectProductQnaList(productId);
    }

    @Transactional
    public void createProductQna(Long productId, Long socialAccountId, String content) {
        SocialAccount account = socialAccountDao.selectById(socialAccountId);
        if (account == null) throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");

        Comu comu = Comu.builder()
                .comuType("BA")
                .eventId(productId)
                .creUser(resolveDisplayName(account))
                .build();
        comuDao.insertComu(comu);

        comuDao.insertComuDet(ComuDet.builder()
                .comuId(comu.getId())
                .reqYn("Y")
                .comuCntn(content)
                .creUser(resolveDisplayName(account))
                .build());
    }

    public List<ComuResponse.Summary> getComuListByOrderId(Long orderId) {
        return comuDao.selectComuListByOrderId(orderId);
    }

    private String resolveDisplayName(SocialAccount account) {
        return StringUtils.isNotBlank(account.getNickname())
                ? account.getNickname()
                : account.getEmail();
    }
}
