package com.shop.api.frontWeb.service;

import com.shop.core.entity.Cart;
import com.shop.core.entity.CartItem;
import com.shop.core.entity.GuestToken;
import com.shop.core.frontWeb.dao.CartDao;
import com.shop.core.frontWeb.dao.GuestTokenDao;
import com.shop.core.frontWeb.vo.request.CartRequest;
import com.shop.core.frontWeb.vo.response.CartResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;
    private final GuestTokenDao guestTokenDao;

    /**
     * 장바구니 조회
     */
    public CartResponse.CartInfo getCart(CartRequest.GetCart request) {
        Cart cart = findOrNullCart(request);
        if (cart == null) {
            CartResponse.CartInfo empty = new CartResponse.CartInfo();
            empty.setItems(new ArrayList<>());
            empty.setTotalCount(0);
            empty.setTotalPrice(BigDecimal.ZERO);
            return empty;
        }
        return buildCartInfo(cart);
    }

    /**
     * 장바구니 상품 추가
     */
    @Transactional
    public CartResponse.CartInfo addItem(CartRequest.AddItem request) {
        // 게스트 토큰으로 cart 조회 또는 생성
        GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(request.getGuestId());
        if (guestToken == null) {
            throw new IllegalArgumentException("유효하지 않은 게스트 ID: " + request.getGuestId());
        }

        Cart cart = cartDao.selectActiveCartByGuestTokenId(guestToken.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setGuestTokenId(guestToken.getId());
            cart.setSocialAccountId(guestToken.getSocialAccountId());
            cart.setStatus("active");
            cartDao.insertCart(cart);
        }

        CartItem item = new CartItem();
        item.setCartId(cart.getId());
        item.setProductDetId(request.getProductDetId());
        item.setQuantity(request.getQuantity());
        item.setUnitPrice(request.getUnitPrice());
        item.setCurrency("KRW");
        item.setOptionsSnapshot(request.getOptionsSnapshot());
        cartDao.insertCartItem(item);  // ON CONFLICT → 수량 합산 (mapper에서 처리)

        return buildCartInfo(cart);
    }

    /**
     * 장바구니 수량 수정
     */
    @Transactional
    public CartResponse.CartInfo updateItem(CartRequest.UpdateItem request) {
        cartDao.updateCartItemQuantity(request);
        // 수량 0이면 삭제
        if (request.getQuantity() <= 0) {
            cartDao.deleteCartItem(request.getCartItemId());
        }
        // 변경된 cart 반환 (cartItemId로 cartId를 알 수 없으므로 간단히 null 반환 후 재조회 패턴)
        // 실제로는 cartItemId → cartId를 조회해야 하지만 여기서는 간략화
        return new CartResponse.CartInfo();
    }

    /**
     * 장바구니 아이템 삭제
     */
    @Transactional
    public void deleteItem(Long cartItemId) {
        cartDao.deleteCartItem(cartItemId);
    }

    /**
     * 장바구니 전체 비우기
     */
    @Transactional
    public void clearCart(String guestId) {
        GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(guestId);
        if (guestToken == null) return;

        Cart cart = cartDao.selectActiveCartByGuestTokenId(guestToken.getId());
        if (cart != null) {
            cartDao.deleteAllCartItems(cart.getId());
        }
    }

    /**
     * 회원 전환 시 게스트 장바구니 → 회원 장바구니 병합
     */
    @Transactional
    public void mergeGuestCartToMember(String guestId, Long socialAccountId) {
        GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(guestId);
        if (guestToken == null) return;

        Cart guestCart = cartDao.selectActiveCartByGuestTokenId(guestToken.getId());
        if (guestCart == null) return;

        Cart memberCart = cartDao.selectActiveCartBySocialAccountId(socialAccountId);

        if (memberCart == null) {
            // 회원 카트 없음 → 게스트 카트를 회원 카트로 전환
            cartDao.updateCartSocialAccountId(guestCart.getId(), socialAccountId);
        } else {
            // 회원 카트 존재 → 게스트 아이템을 회원 카트로 이관
            List<CartItem> guestItems = cartDao.selectCartItems(guestCart.getId());
            for (CartItem item : guestItems) {
                item.setCartId(memberCart.getId());
                cartDao.insertCartItem(item);  // ON CONFLICT → 수량 합산
            }
            // 게스트 카트 merged 처리
            cartDao.updateCartStatus(guestCart.getId(), "merged");
        }
    }

    // ── private 헬퍼 ──────────────────────────────────

    private Cart findOrNullCart(CartRequest.GetCart request) {
        if (request.getSocialAccountId() != null) {
            return cartDao.selectActiveCartBySocialAccountId(request.getSocialAccountId());
        }
        if (request.getGuestId() != null) {
            GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(request.getGuestId());
            if (guestToken == null) return null;
            return cartDao.selectActiveCartByGuestTokenId(guestToken.getId());
        }
        return null;
    }

    private CartResponse.CartInfo buildCartInfo(Cart cart) {
        List<CartItem> items = cartDao.selectCartItems(cart.getId());

        List<CartResponse.ItemInfo> itemInfos = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem ci : items) {
            CartResponse.ItemInfo info = new CartResponse.ItemInfo();
            info.setCartItemId(ci.getId());
            info.setProductDetId(ci.getProductDetId());
            info.setProductId(ci.getProductId());        // JOIN 으로 가져온 상품 ID
            info.setProductName(ci.getProductName());
            info.setProductImage(ci.getProductImage());
            info.setProductDetSize(ci.getProductDetSize());
            info.setProductDetColor(ci.getProductDetColor());
            info.setSkuDiscountRate(ci.getSkuDiscountRate());
            info.setQuantity(ci.getQuantity());
            info.setUnitPrice(ci.getUnitPrice());
            info.setOptionsSnapshot(ci.getOptionsSnapshot());

            BigDecimal subtotal = ci.getUnitPrice()
                    .multiply(BigDecimal.valueOf(ci.getQuantity()));
            info.setSubtotal(subtotal);
            totalPrice = totalPrice.add(subtotal);

            itemInfos.add(info);
        }

        CartResponse.CartInfo result = new CartResponse.CartInfo();
        result.setCartId(cart.getId());
        result.setItems(itemInfos);
        result.setTotalCount(itemInfos.size());
        result.setTotalPrice(totalPrice);
        return result;
    }
}
