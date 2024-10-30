package pintoss.giftmall.domains.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.domains.cart.domain.Cart;
import pintoss.giftmall.domains.cart.dto.CartRequest;
import pintoss.giftmall.domains.cart.dto.CartResponse;
import pintoss.giftmall.domains.cart.infra.CartRepository;
import pintoss.giftmall.domains.cart.infra.CartReader;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.PriceCategoryReader;
import pintoss.giftmall.domains.product.infra.ProductReader;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserReader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartReader cartReader;
    private final ProductReader productReader;
    private final UserReader userReader;
    private final PriceCategoryReader priceCategoryReader;

    public Long addToCart(Long userId, CartRequest requestDTO) {
        Product product = productReader.findById(requestDTO.getProductId());
        PriceCategory priceCategory = priceCategoryReader.findById(requestDTO.getPriceCategoryId());
        User user = userReader.findById(userId);

        // CartResponse 대신 Cart를 반환하도록 메서드를 호출합니다.
        Optional<CartResponse> existingCartOptional = cartRepository
                .findByUserAndProductAndPriceCategory(user, product, priceCategory); // CartResponse를 Cart로 변환하는 메서드를 추가합니다.

        Cart cart;
        if (existingCartOptional.isPresent()) {
            cart = existingCartOptional.get().toEntity(existingCartOptional.get(),productReader, priceCategoryReader); // CartResponse를 Cart로 변환
            cart.updateQuantity(cart.getQuantity() + requestDTO.getQuantity());
        } else {
            cart = requestDTO.toEntity(product, priceCategory, user);
            cartRepository.save(cart);
        }

        return cart.getId();
    }


    @Transactional(readOnly = true)
    public List<CartResponse> getCartItems(Long userId) {
        userReader.findById(userId);

        List<Cart> cartItems = cartRepository.findAllByUserId(userId);

        return cartItems.stream()
                .map(CartResponse::fromEntity)
                .collect(Collectors.toList());
    }


    public void updateCartItem(Long cartItemId, int quantity) {
        Cart cart = cartReader.findById(cartItemId);

        cart.updateQuantity(quantity);
    }

    public void deleteCartItem(Long cartItemId) {
        Cart cart = cartReader.findById(cartItemId);

        cartRepository.delete(cart);
    }

    public void deleteAllCartItems(Long userId) {
        List<Cart> cartItems = cartRepository.findAllByUserId(userId);
        cartRepository.deleteAll(cartItems);
    }

    public void updateAllCartItemsToPayMethod(Long userId, PayMethod newPayMethod) {
        List<Cart> cartItems = cartRepository.findAllByUserId(userId);

        for (Cart cart : cartItems) {
            cart.updatePayMethod(newPayMethod);
        }
    }


}
