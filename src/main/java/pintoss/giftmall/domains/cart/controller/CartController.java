package pintoss.giftmall.domains.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.cart.dto.CartRequest;
import pintoss.giftmall.domains.cart.dto.CartResponse;
import pintoss.giftmall.domains.cart.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{product_id}")
    public ApiResponse<Long> addToCart(@PathVariable("product_id") Long productId, @RequestBody CartRequest requestDTO, @RequestParam Long userId) {
        CartRequest updatedRequest = CartRequest.builder()
                .productId(productId)
                .quantity(requestDTO.getQuantity())
                .price(requestDTO.getPrice())
                .checkoutMethod(requestDTO.getCheckoutMethod())
                .build();

        Long cartItemId = cartService.addToCart(userId, updatedRequest);
        return ApiResponse.ok(cartItemId);
    }

    @GetMapping
    public ApiResponse<List<CartResponse>> getCartItems(@RequestParam Long userId) {
        List<CartResponse> cartItems = cartService.getCartItems(userId);
        return ApiResponse.ok(cartItems);
    }

    @PatchMapping("/{cart_item_id}")
    public ApiResponse<String> updateCartItem(@PathVariable Long cart_item_id, @RequestBody int quantity) {
        cartService.updateCartItem(cart_item_id, quantity);
        return ApiResponse.ok("장바구니 상품의 수량이 수정되었습니다.");
    }

    @DeleteMapping("/{cart_item_id}")
    public ApiResponse<String> deleteCartItem(@PathVariable Long cart_item_id) {
        cartService.deleteCartItem(cart_item_id);
        return ApiResponse.ok("장바구니 상품이 삭제되었습니다.");
    }

}
