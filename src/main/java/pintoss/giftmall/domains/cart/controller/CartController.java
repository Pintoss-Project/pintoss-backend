package pintoss.giftmall.domains.cart.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.cart.dto.CartRequest;
import pintoss.giftmall.domains.cart.dto.CartResponse;
import pintoss.giftmall.domains.cart.service.CartService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    @PostMapping("/register")
    public ApiResponse<List<Long>> addToCart(
            @RequestBody @Valid List<CartRequest> requestDTOs,
            @RequestParam(name = "userId") @NotNull Long userId) {

        List<Long> cartItemIds = requestDTOs.stream()
                .map(requestDTO -> cartService.addToCart(userId, requestDTO.getProductId(), requestDTO))
                .collect(Collectors.toList());

        return ApiResponse.ok(cartItemIds);
    }


    @GetMapping
    public ApiResponse<List<CartResponse>> getCartItems(@RequestParam(name = "userId") @NotNull Long userId) {
        List<CartResponse> cartItems = cartService.getCartItems(userId);
        return ApiResponse.ok(cartItems);
    }

    @PatchMapping("/{cartItemId}")
    public ApiResponse<String> updateCartItem(@PathVariable("cartItemId") Long cartItemId, @RequestBody @Valid int quantity) {
        cartService.updateCartItem(cartItemId, quantity);
        return ApiResponse.ok("장바구니 상품의 수량이 수정되었습니다.");
    }

    @DeleteMapping("/{cartItemId}")
    public ApiResponse<String> deleteCartItem(@PathVariable("cartItemId") Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ApiResponse.ok("장바구니 상품이 삭제되었습니다.");
    }

    @DeleteMapping("/all")
    public ApiResponse<String> deleteAllCartItems(@RequestParam(name= "userId") @NotNull Long userId) {
        cartService.deleteAllCartItems(userId);
        return ApiResponse.ok("모든 장바구니 상품이 삭제되었습니다.");
    }

    @PatchMapping("/update-paymethod")
    public ApiResponse<String> updateCartPayMethod(@RequestParam(name= "userId") @NotNull Long userId, @RequestParam(name= "newPayMethod") @NotNull PayMethod newPayMethod) {
        cartService.updateAllCartItemsToPayMethod(userId, newPayMethod);
        return ApiResponse.ok("장바구니의 결제 수단이 업데이트되었습니다.");
    }

}