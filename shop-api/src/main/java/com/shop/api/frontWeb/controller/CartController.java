package com.shop.api.frontWeb.controller;

import com.shop.api.frontWeb.service.CartService;
import com.shop.core.annotations.NotAuthRequired;
import com.shop.core.biz.system.vo.response.ApiResponse;
import com.shop.core.enums.ApiResultCode;
import com.shop.core.frontWeb.vo.request.CartRequest;
import com.shop.core.frontWeb.vo.response.CartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/frontWeb/cart")
@Tag(name = "CartController", description = "FO 장바구니 관련 API")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 조회
     * - guestId 또는 memberId 중 하나로 조회
     */
    @NotAuthRequired
    @GetMapping
    @Operation(summary = "장바구니 조회",
               description = "guestId(게스트) 또는 memberId(회원) 로 장바구니 조회")
    public ApiResponse<CartResponse.CartInfo> getCart(
            @Parameter(description = "게스트 ID")      @RequestParam(required = false) String guestId,
            @Parameter(description = "소셜 계정 ID")   @RequestParam(required = false) Long socialAccountId) {

        CartRequest.GetCart request = new CartRequest.GetCart();
        request.setGuestId(guestId);
        request.setSocialAccountId(socialAccountId);

        CartResponse.CartInfo result = cartService.getCart(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS, result);
    }

    /**
     * 장바구니 상품 추가
     * - 동일 상품+옵션 이미 담겨 있으면 수량 합산 (ON CONFLICT)
     */
    @NotAuthRequired
    @PostMapping("/item")
    @Operation(summary = "장바구니 상품 추가",
               description = "동일 상품+옵션이 이미 있으면 수량 합산")
    public ApiResponse<CartResponse.CartInfo> addItem(@RequestBody CartRequest.AddItem request) {
        try {
            CartResponse.CartInfo result = cartService.addItem(request);
            return new ApiResponse<>(ApiResultCode.SUCCESS, result);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(ApiResultCode.FAIL, e.getMessage());
        }
    }

    /**
     * 장바구니 수량 수정
     * - quantity = 0 이면 해당 아이템 삭제 처리
     */
    @NotAuthRequired
    @PutMapping("/item")
    @Operation(summary = "장바구니 수량 수정",
               description = "quantity 0 입력 시 해당 아이템 자동 삭제")
    public ApiResponse<Void> updateItem(@RequestBody CartRequest.UpdateItem request) {
        cartService.updateItem(request);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 장바구니 아이템 단건 삭제
     */
    @NotAuthRequired
    @DeleteMapping("/item/{cartItemId}")
    @Operation(summary = "장바구니 아이템 삭제")
    public ApiResponse<Void> deleteItem(
            @Parameter(description = "장바구니 아이템 ID") @PathVariable Long cartItemId) {
        cartService.deleteItem(cartItemId);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 장바구니 전체 비우기
     */
    @NotAuthRequired
    @DeleteMapping("/clear")
    @Operation(summary = "장바구니 전체 비우기")
    public ApiResponse<Void> clearCart(
            @Parameter(description = "게스트 ID") @RequestParam String guestId) {
        cartService.clearCart(guestId);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }

    /**
     * 게스트 → 회원 장바구니 병합
     * - 소셜 로그인 완료 후 호출
     */
    @NotAuthRequired
    @PostMapping("/merge")
    @Operation(summary = "게스트 → 회원 장바구니 병합",
               description = "소셜 로그인 완료 후 게스트 장바구니를 회원 장바구니에 병합")
    public ApiResponse<Void> mergeCart(
            @Parameter(description = "게스트 ID")    @RequestParam String guestId,
            @Parameter(description = "소셜 계정 ID") @RequestParam Long socialAccountId) {
        cartService.mergeGuestCartToMember(guestId, socialAccountId);
        return new ApiResponse<>(ApiResultCode.SUCCESS);
    }
}
