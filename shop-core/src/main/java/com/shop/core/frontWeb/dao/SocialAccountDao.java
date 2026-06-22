package com.shop.core.frontWeb.dao;

import com.shop.core.entity.MemberToken;
import com.shop.core.entity.SocialAccount;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SocialAccountDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NS       = "com.shop.mapper.frontWeb.SocialAccount.";
    private static final String NS_TOKEN = "com.shop.mapper.frontWeb.MemberToken.";

    // ── 소셜 계정(회원) 조회 ────────────────────────────────────────

    /** provider + providerId 로 소셜 계정 조회 */
    public SocialAccount selectByProviderAndProviderId(String provider, String providerId) {
        SocialAccount param = new SocialAccount();
        param.setProvider(provider);
        param.setProviderId(providerId);
        return sqlSession.selectOne(NS + "selectByProviderAndProviderId", param);
    }

    /** PK(id) 로 소셜 계정 조회 */
    public SocialAccount selectById(Long id) {
        return sqlSession.selectOne(NS + "selectById", id);
    }

    /** 소셜 계정 신규 등록 (useGeneratedKeys → id 자동 세팅) */
    public int insertSocialAccount(SocialAccount socialAccount) {
        return sqlSession.insert(NS + "insertSocialAccount", socialAccount);
    }

    /** 프로필 정보 갱신 (닉네임, 이미지, 이메일) */
    public int updateSocialAccount(SocialAccount socialAccount) {
        return sqlSession.update(NS + "updateSocialAccount", socialAccount);
    }

    /** 최종 로그인 일시 갱신 */
    public int updateLastLoginTm(Long id) {
        return sqlSession.update(NS + "updateLastLoginTm", id);
    }

    /** 회원 탈퇴: 개인정보 마스킹 + 상태 withdrawn */
    public int withdrawSocialAccount(Long id) {
        return sqlSession.update(NS + "withdrawSocialAccount", id);
    }

    // ── 토큰 관리 ───────────────────────────────────────────────────

    /** 토큰 upsert (member_id UNIQUE → 있으면 갱신) */
    public int upsertMemberToken(MemberToken memberToken) {
        return sqlSession.insert(NS_TOKEN + "upsertMemberToken", memberToken);
    }

    /** access_token 으로 토큰 조회 */
    public MemberToken selectTokenByAccessToken(String accessToken) {
        return sqlSession.selectOne(NS_TOKEN + "selectTokenByAccessToken", accessToken);
    }

    /** 로그아웃: member_id(social_account.id) 로 토큰 삭제 */
    public int deleteTokenByMemberId(Long memberId) {
        return sqlSession.delete(NS_TOKEN + "deleteTokenByMemberId", memberId);
    }
}
