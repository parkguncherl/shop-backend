package com.shop.core.frontWeb.dao;

import com.shop.core.entity.Cart;
import com.shop.core.entity.CartItem;
import com.shop.core.frontWeb.vo.request.CartRequest;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartDao {

    private final SqlSessionTemplate sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.frontWeb.Cart.";

    /** 게스트 토큰 ID로 활성 장바구니 조회 */
    public Cart selectActiveCartByGuestTokenId(Long guestTokenId) {
        return sqlSession.selectOne(NAMESPACE + "selectActiveCartByGuestTokenId", guestTokenId);
    }

    /** 소셜 계정 ID로 활성 장바구니 조회 */
    public Cart selectActiveCartBySocialAccountId(Long socialAccountId) {
        return sqlSession.selectOne(NAMESPACE + "selectActiveCartBySocialAccountId", socialAccountId);
    }

    /** 장바구니 아이템 목록 조회 */
    public List<CartItem> selectCartItems(Long cartId) {
        return sqlSession.selectList(NAMESPACE + "selectCartItems", cartId);
    }

    /** 장바구니 생성 */
    public int insertCart(Cart cart) {
        return sqlSession.insert(NAMESPACE + "insertCart", cart);
    }

    /** 장바구니 아이템 추가 */
    public int insertCartItem(CartItem cartItem) {
        return sqlSession.insert(NAMESPACE + "insertCartItem", cartItem);
    }

    /** 장바구니 아이템 수량 수정 */
    public int updateCartItemQuantity(CartRequest.UpdateItem request) {
        return sqlSession.update(NAMESPACE + "updateCartItemQuantity", request);
    }

    /** 장바구니 아이템 삭제 */
    public int deleteCartItem(Long cartItemId) {
        return sqlSession.delete(NAMESPACE + "deleteCartItem", cartItemId);
    }

    /** 장바구니 전체 비우기 */
    public int deleteAllCartItems(Long cartId) {
        return sqlSession.delete(NAMESPACE + "deleteAllCartItems", cartId);
    }

    /** 회원 전환 시 장바구니 social_account_id 업데이트 */
    public int updateCartSocialAccountId(Long cartId, Long socialAccountId) {
        Cart param = new Cart();
        param.setId(cartId);
        param.setSocialAccountId(socialAccountId);
        return sqlSession.update(NAMESPACE + "updateCartSocialAccountId", param);
    }

    /** 로그인 시 게스트 카트 → 회원 귀속 (guestId 로 social_account_id 일괄 업데이트) */
    public int updateCartSocialAccountIdByGuestId(Long socialAccountId, String guestId) {
        return sqlSession.update(NAMESPACE + "updateCartSocialAccountIdByGuestId",
                java.util.Map.of("socialAccountId", socialAccountId, "guestId", guestId));
    }

    /** 장바구니 상태 변경 (merged, ordered 등) */
    public int updateCartStatus(Long cartId, String status) {
        Cart param = new Cart();
        param.setId(cartId);
        param.setStatus(status);
        return sqlSession.update(NAMESPACE + "updateCartStatus", param);
    }
}
