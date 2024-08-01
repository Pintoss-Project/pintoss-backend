package pintoss.giftmall.domains.cart.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/{product_id}")
    public ApiResponse<Long> addToCart(@PathVariable("product_id") Long productId, @Valid @RequestBody CartRequest requestDTO, @NotNull @RequestParam Long userId) {
        CartRequest updatedRequest = CartRequest.builder()
                .productId(productId)
                .quantity(requestDTO.getQuantity())
                .price(requestDTO.getPrice())
                .payMethod(requestDTO.getPayMethod())
                .build();

        Long cartItemId = cartService.addToCart(userId, updatedRequest);
        return ApiResponse.ok(cartItemId);
    }

    @GetMapping
    public ApiResponse<List<CartResponse>> getCartItems(@NotNull @RequestParam Long userId) {
        List<CartResponse> cartItems = cartService.getCartItems(userId);
        return ApiResponse.ok(cartItems);
    }

    @PatchMapping("/{cart_item_id}")
    public ApiResponse<String> updateCartItem(@PathVariable("cart_item_id") @NotNull Long cartItemId, @RequestBody int quantity) {
        cartService.updateCartItem(cartItemId, quantity);
        return ApiResponse.ok("장바구니 상품의 수량이 수정되었습니다.");
    }

    @DeleteMapping("/{cart_item_id}")
    public ApiResponse<String> deleteCartItem(@PathVariable("cart_item_id") @NotNull Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ApiResponse.ok("장바구니 상품이 삭제되었습니다.");
    }

}
