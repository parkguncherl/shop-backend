package com.shop.core.frontWeb.dao;

import com.shop.core.entity.Cart;
import com.shop.core.enums.CartStatus;
import com.shop.core.frontWeb.vo.request.CartRequest;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CartDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.Cart.";

    /** 소셜 계정 ID로 활성 장바구니 아이템 목록 조회 */
    public List<Cart> selectActiveCartsBySocialAccountId(Long socialAccountId) {
        return sqlSession.selectList(NAMESPACE + "selectActiveCartsBySocialAccountId", socialAccountId);
    }

    /** 게스트 토큰 ID로 활성 장바구니 아이템 목록 조회 */
    public List<Cart> selectActiveCartsByGuestTokenId(Long guestTokenId) {
        return sqlSession.selectList(NAMESPACE + "selectActiveCartsByGuestTokenId", guestTokenId);
    }

    /** 특정 상품이 이미 담겨 있는지 확인 (소셜 계정 기준) */
    public Cart selectActiveCartItemBySocialAccountId(Long socialAccountId, Long productDetId) {
        return sqlSession.selectOne(NAMESPACE + "selectActiveCartItemBySocialAccountId",
                Map.of("socialAccountId", socialAccountId, "productDetId", productDetId));
    }

    /** 특정 상품이 이미 담겨 있는지 확인 (게스트 기준) */
    public Cart selectActiveCartItemByGuestTokenId(Long guestTokenId, Long productDetId) {
        return sqlSession.selectOne(NAMESPACE + "selectActiveCartItemByGuestTokenId",
                Map.of("guestTokenId", guestTokenId, "productDetId", productDetId));
    }

    /** 장바구니 아이템 추가 */
    public int insertCart(Cart cart) {
        return sqlSession.insert(NAMESPACE + "insertCart", cart);
    }

    /** 수량 수정 (TB_CART.id 기준) */
    public int updateCartItemQuantity(CartRequest.UpdateItem request) {
        return sqlSession.update(NAMESPACE + "updateCartItemQuantity", request);
    }

    /** 수량 직접 설정 (병합 시 사용) */
    public int updateCartItemQuantityById(Long cartId, Integer quantity) {
        return sqlSession.update(NAMESPACE + "updateCartItemQuantityById",
                Map.of("cartId", cartId, "quantity", quantity));
    }

    /** 단건 삭제 (del_yn = 'Y') */
    public int deleteCartItem(Long cartId) {
        return sqlSession.update(NAMESPACE + "deleteCartItem", cartId);
    }

    /** 소셜 계정의 활성 아이템 전체 주문완료 처리 (status='O', del_yn='Y') */
    public int markOrderedBySocialAccountId(Long socialAccountId) {
        return sqlSession.update(NAMESPACE + "markOrderedBySocialAccountId", socialAccountId);
    }

    /** 게스트 토큰의 활성 아이템 전체 삭제 처리 (del_yn='Y') */
    public int markDeletedByGuestTokenId(Long guestTokenId) {
        return sqlSession.update(NAMESPACE + "markDeletedByGuestTokenId", guestTokenId);
    }

    /** 로그인 시 게스트 ID 기준으로 cart의 social_account_id 일괄 업데이트 */
    public int updateCartSocialAccountIdByGuestId(Long socialAccountId, String guestId) {
        return sqlSession.update(NAMESPACE + "updateCartSocialAccountIdByGuestId",
                Map.of("socialAccountId", socialAccountId, "guestId", guestId));
    }

    /** 회원 전환 시 cart의 social_account_id 업데이트 (단건) */
    public int updateCartSocialAccountId(Long cartId, Long socialAccountId) {
        return sqlSession.update(NAMESPACE + "updateCartSocialAccountId",
                Map.of("id", cartId, "socialAccountId", socialAccountId));
    }

    /** 상태 변경 (id 기준) */
    public int updateCartStatus(Long cartId, CartStatus status) {
        return sqlSession.update(NAMESPACE + "updateCartStatus",
                Map.of("id", cartId, "status", status.getCode()));
    }
}
