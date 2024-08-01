package pintoss.giftmall.domains.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.cart.dto.CartRequest;
import pintoss.giftmall.domains.cart.dto.CartResponse;
import pintoss.giftmall.domains.cart.infra.CartRepository;
import pintoss.giftmall.domains.cart.infra.CartRepositoryReader;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.product.infra.ProductRepositoryReader;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;
import pintoss.giftmall.domains.user.infra.UserRepositoryReader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartRepositoryReader cartRepositoryReader;
    private final ProductRepositoryReader productRepositoryReader;
    private final UserRepositoryReader userRepositoryReader;

    public Long addToCart(Long userId, CartRequest requestDTO) {
        Product product = productRepositoryReader.findById(requestDTO.getProductId());
        User user = userRepositoryReader.findById(userId);

        Cart cart = requestDTO.toEntity(product, user);
        cartRepository.save(cart);

        return cart.getId();
    }

    @Transactional(readOnly = true)
    public List<CartResponse> getCartItems(Long userId) {
        userRepositoryReader.findById(userId);

        List<Cart> cartItems = cartRepository.findAllByUserId(userId);

        return cartItems.stream()
                .map(CartResponse::fromEntity)
                .collect(Collectors.toList());
    }


    public void updateCartItem(Long cartItemId, int quantity) {
        Cart cart = cartRepositoryReader.findById(cartItemId);

        cart.updateQuantity(quantity);
    }

    public void deleteCartItem(Long cartItemId) {
        Cart cart = cartRepositoryReader.findById(cartItemId);

        cartRepository.delete(cart);
    }
}
