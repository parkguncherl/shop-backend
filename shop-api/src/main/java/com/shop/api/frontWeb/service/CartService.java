package com.shop.api.frontWeb.service;

import com.shop.core.entity.Cart;
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
        List<Cart> items = findActiveItems(request);
        return buildCartInfo(items);
    }

    /**
     * 장바구니 상품 추가
     * - 이미 담긴 상품이면 수량 합산
     */
    @Transactional
    public CartResponse.CartInfo addItem(CartRequest.AddItem request) {
        Long socialAccountId;
        Long guestTokenId;

        if (request.getSocialAccountId() != null) {
            // 로그인 사용자 - guestToken 조회 불필요
            socialAccountId = request.getSocialAccountId();
            guestTokenId    = null;
        } else {
            GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(request.getGuestId());
            if (guestToken == null) {
                throw new IllegalArgumentException("유효하지 않은 게스트 ID: " + request.getGuestId());
            }
            socialAccountId = guestToken.getSocialAccountId();
            guestTokenId    = guestToken.getId();
        }

        // 이미 담긴 상품 확인
        Cart existing = (socialAccountId != null)
                ? cartDao.selectActiveCartItemBySocialAccountId(socialAccountId, request.getProductDetId())
                : cartDao.selectActiveCartItemByGuestTokenId(guestTokenId, request.getProductDetId());

        if (existing != null) {
            // 수량 합산
            CartRequest.UpdateItem updateRequest = new CartRequest.UpdateItem();
            updateRequest.setCartId(existing.getId());
            updateRequest.setQuantity(existing.getQuantity() + request.getQuantity());
            cartDao.updateCartItemQuantity(updateRequest);
        } else {
            // 신규 추가 - 가격은 상품 테이블에서 조회
            BigDecimal unitPrice = cartDao.selectSellAmtByProductDetId(request.getProductDetId());
            Cart item = Cart.builder()
                    .socialAccountId(socialAccountId)
                    .guestTokenId(guestTokenId)
                    .productDetId(request.getProductDetId())
                    .quantity(request.getQuantity())
                    .unitPrice(unitPrice)
                    .currency("KRW")
                    .build();
            cartDao.insertCart(item);
        }

        // 최신 장바구니 반환
        List<Cart> items = (socialAccountId != null)
                ? cartDao.selectActiveCartsBySocialAccountId(socialAccountId)
                : cartDao.selectActiveCartsByGuestTokenId(guestTokenId);
        return buildCartInfo(items);
    }

    /**
     * 수량 수정 (quantity=0 → 삭제)
     */
    @Transactional
    public void updateItem(CartRequest.UpdateItem request) {
        if (request.getQuantity() <= 0) {
            cartDao.deleteCartItem(request.getCartId());
        } else {
            cartDao.updateCartItemQuantity(request);
        }
    }

    /**
     * 장바구니 아이템 단건 삭제
     */
    @Transactional
    public void deleteItem(Long cartId) {
        cartDao.deleteCartItem(cartId);
    }

    /**
     * 게스트 장바구니 전체 비우기
     */
    @Transactional
    public void clearCart(String guestId) {
        GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(guestId);
        if (guestToken == null) return;
        cartDao.markDeletedByGuestTokenId(guestToken.getId());
    }

    /**
     * 회원 전환 시 게스트 장바구니 → 회원 장바구니 병합
     */
    @Transactional
    public void mergeGuestCartToMember(String guestId, Long socialAccountId) {
        GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(guestId);
        if (guestToken == null) return;

        List<Cart> guestItems = cartDao.selectActiveCartsByGuestTokenId(guestToken.getId());
        if (guestItems.isEmpty()) return;

        for (Cart guestItem : guestItems) {
            // 회원 장바구니에 이미 같은 상품이 있는지 확인
            Cart memberItem = cartDao.selectActiveCartItemBySocialAccountId(
                    socialAccountId, guestItem.getProductDetId());

            if (memberItem != null) {
                // 수량 합산
                cartDao.updateCartItemQuantityById(
                        memberItem.getId(), memberItem.getQuantity() + guestItem.getQuantity());
            } else {
                // 회원 카트로 이관
                Cart newItem = Cart.builder()
                        .socialAccountId(socialAccountId)
                        .productDetId(guestItem.getProductDetId())
                        .quantity(guestItem.getQuantity())
                        .unitPrice(guestItem.getUnitPrice())
                        .currency(guestItem.getCurrency())
                        .build();
                cartDao.insertCart(newItem);
            }
        }

        // 게스트 아이템 삭제 처리
        cartDao.markDeletedByGuestTokenId(guestToken.getId());
    }

    // ── private 헬퍼 ───────────────────────────────────────

    private List<Cart> findActiveItems(CartRequest.GetCart request) {
        if (request.getSocialAccountId() != null) {
            return cartDao.selectActiveCartsBySocialAccountId(request.getSocialAccountId());
        }
        if (request.getGuestId() != null) {
            GuestToken guestToken = guestTokenDao.selectGuestTokenByGuestId(request.getGuestId());
            if (guestToken == null) return new ArrayList<>();
            return cartDao.selectActiveCartsByGuestTokenId(guestToken.getId());
        }
        return new ArrayList<>();
    }

    private CartResponse.CartInfo buildCartInfo(List<Cart> items) {
        List<CartResponse.ItemInfo> itemInfos = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Cart c : items) {
            CartResponse.ItemInfo info = new CartResponse.ItemInfo();
            info.setCartId(c.getId());
            info.setProductId(c.getProductId());
            info.setProductDetId(c.getProductDetId());
            info.setProductName(c.getProductName());
            info.setProductImage(c.getProductImage());
            info.setProductDetSize(c.getProductDetSize());
            info.setProductDetColor(c.getProductDetColor());
            info.setSkuDiscountRate(c.getSkuDiscountRate());
            info.setQuantity(c.getQuantity());
            info.setUnitPrice(c.getUnitPrice());

            BigDecimal subtotal = c.getUnitPrice() != null
                    ? c.getUnitPrice().multiply(BigDecimal.valueOf(c.getQuantity()))
                    : BigDecimal.ZERO;
            info.setSubtotal(subtotal);
            totalPrice = totalPrice.add(subtotal);

            itemInfos.add(info);
        }

        CartResponse.CartInfo result = new CartResponse.CartInfo();
        result.setItems(itemInfos);
        result.setTotalCount(itemInfos.size());
        result.setTotalPrice(totalPrice);
        return result;
    }
}
