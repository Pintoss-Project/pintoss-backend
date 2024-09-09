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

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    @PostMapping("/{productId}")
    public ApiResponse<Long> addToCart(@PathVariable(name = "productId") Long productId, @RequestBody @Valid CartRequest requestDTO, @RequestParam(name = "userId") @NotNull Long userId) {
        CartRequest updatedRequest = CartRequest.builder()
                .priceCategoryId(requestDTO.getPriceCategoryId())
                .quantity(requestDTO.getQuantity())
                .payMethod(requestDTO.getPayMethod())
                .build();

        Long cartItemId = cartService.addToCart(userId, productId, updatedRequest);
        return ApiResponse.ok(cartItemId);
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
