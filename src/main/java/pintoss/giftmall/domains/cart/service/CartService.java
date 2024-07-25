package pintoss.giftmall.domains.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.cart.dto.CartRequest;
import pintoss.giftmall.domains.cart.dto.CartResponse;
import pintoss.giftmall.domains.cart.infra.CartRepository;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addToCart(Long userId, CartRequest requestDTO) {
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product_id!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user_id!"));

        Cart cart = requestDTO.toEntity(product, user);
        cartRepository.save(cart);

        return cart.getId();
    }

    @Transactional(readOnly = true)
    public List<CartResponse> getCartItems(Long userId) {
        List<Cart> cartItems = cartRepository.findAllByUserId(userId);

        return cartItems.stream()
                .map(CartResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCartItem(Long cartItemId, int quantity) {
        Cart cart = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cart_item_id!"));
        cart.updateQuantity(quantity);
    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        Cart cart = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cart_item_id!"));
        cartRepository.delete(cart);
    }

}
