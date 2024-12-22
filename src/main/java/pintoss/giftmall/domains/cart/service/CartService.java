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
import pintoss.giftmall.domains.product.domain.ProductImage;
import pintoss.giftmall.domains.product.infra.PriceCategoryReader;
import pintoss.giftmall.domains.product.infra.ProductImageRepository;
import pintoss.giftmall.domains.product.infra.ProductReader;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserReader;

import java.math.BigDecimal;
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
    private final ProductImageRepository productImageRepository;

    public Long addToCart(Long userId, Long productId, CartRequest requestDTO) {
        Product product = productReader.findById(productId);
        PriceCategory priceCategory = priceCategoryReader.findById(requestDTO.getPriceCategoryId());
        User user = userReader.findById(userId);

        Optional<CartResponse> existingCartOptional = cartRepository.findByUserAndProductAndPriceCategory(user, product, priceCategory);

        Cart cart;
        if (existingCartOptional.isPresent()) {
            cart = existingCartOptional.get().toEntity(existingCartOptional.get(),productReader,priceCategoryReader);
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
                .map(cart -> {
                    String logoImageUrl = productImageRepository.findByProductId(cart.getProduct().getId())
                            .stream()
                            .findFirst()
                            .map(ProductImage::getUrl)
                            .orElse(null);
                    return CartResponse.fromEntity(cart, logoImageUrl);
                })
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
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어 있습니다.");
        }
        cartRepository.deleteAll(cartItems);
    }

    public void updateAllCartItemsToPayMethod(Long userId, PayMethod newPayMethod) {
        List<Cart> cartItems = cartRepository.findAllByUserId(userId);

        for (Cart cart : cartItems) {
            cart.updatePayMethod(newPayMethod);
        }
    }

}

