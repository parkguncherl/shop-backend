package com.shop.api.frontWeb.service;

import com.shop.core.entity.MemberToken;
import com.shop.core.entity.SocialAccount;
import com.shop.core.frontWeb.dao.GuestTokenDao;
import com.shop.core.frontWeb.dao.SocialAccountDao;
import com.shop.core.frontWeb.vo.request.SocialLoginRequest;
import com.shop.core.frontWeb.vo.response.FrontMemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FrontLoginService {

    private final SocialAccountDao socialAccountDao;
    private final GuestTokenDao    guestTokenDao;
    private final MemberJwtService memberJwtService;

    /**
     * 소셜 로그인 콜백 처리
     *
     * 흐름:
     *  1) TB_SOCIAL_ACCOUNT 에서 provider + providerId 로 기존 계정 조회
     *  2) 없으면 신규 등록
     *  3) 프로필 갱신 (닉네임, 이미지, 이메일)
     *  4) 최종 로그인 일시 갱신
     *  5) JWT 발급 → TB_MEMBER_TOKEN upsert
     *  6) 게스트 토큰 → 회원 연결 (장바구니 병합 트리거)
     */
    @Transactional
    public FrontMemberResponse.Token socialLogin(SocialLoginRequest.Callback request) {
        boolean isNewMember = false;

        // ── Step 1: 기존 소셜 계정 조회 ──
        SocialAccount account = socialAccountDao.selectByProviderAndProviderId(
                request.getProvider(), request.getProviderId()
        );

        if (account == null) {
            // ── Step 2: 신규 등록 ──
            log.debug("소셜 신규 가입: provider={}, email={}", request.getProvider(), request.getEmail());

            account = SocialAccount.builder()
                    .provider(request.getProvider())
                    .providerId(request.getProviderId())
                    .email(request.getEmail())
                    .nickname(request.getNickname())
                    .profileImage(request.getProfileImage())
                    .partnerId(1)
                    .build();

            socialAccountDao.insertSocialAccount(account);  // id 자동 세팅 (useGeneratedKeys)
            isNewMember = true;

        } else {
            // ── Step 3: 프로필 갱신 ──
            log.debug("소셜 기존 로그인: provider={}, id={}", request.getProvider(), account.getId());
            account.setNickname(request.getNickname());
            account.setProfileImage(request.getProfileImage());
            account.setEmail(request.getEmail());
            socialAccountDao.updateSocialAccount(account);
        }

        // ── Step 4: 최종 로그인 일시 갱신 ──
        socialAccountDao.updateLastLoginTm(account.getId());

        // ── Step 5: JWT 발급 ──
        MemberToken token = memberJwtService.issueToken(account);

        // ── Step 6: 게스트 → 회원 연결 ──
        if (StringUtils.isNotBlank(request.getGuestId())) {
            guestTokenDao.updateMemberIdByGuestId(
                    String.valueOf(account.getId()), request.getGuestId()
            );
        }

        // ── 응답 구성 ──
        FrontMemberResponse.Token response = new FrontMemberResponse.Token();
        response.setMemberId(account.getId());
        response.setProvider(account.getProvider());
        response.setEmail(account.getEmail());
        response.setNickname(account.getNickname());
        response.setProfileImage(account.getProfileImage());
        response.setAccessToken(token.getAccessToken());
        response.setRefreshToken(token.getRefreshToken());
        response.setNewMember(isNewMember);
        return response;
    }
}
