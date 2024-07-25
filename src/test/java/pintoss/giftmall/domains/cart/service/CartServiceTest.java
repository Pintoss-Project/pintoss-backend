package pintoss.giftmall.domains.cart.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintoss.giftmall.domains.cart.dto.CartRequest;
import pintoss.giftmall.domains.cart.dto.CartResponse;
import pintoss.giftmall.domains.cart.infra.CartRepository;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PriceCategoryRepository priceCategoryRepository;

    private Long productId;
    private Long userId;
    private Long cartItemId;

    @BeforeEach
    void setUp() {
        Product product = new Product("문화상품권", true, BigDecimal.ONE, BigDecimal.ZERO, "http://example.com", "1544-4567", "상세설명", "발행처", "cbm");
        productRepository.save(product);
        productId = product.getId();

        PriceCategory priceCategory = new PriceCategory("3천원권", 3000, 100, product);
        priceCategoryRepository.save(priceCategory);

        User user = new User("user@example.com", "유저1", "password", "010-1234-5678");
        userRepository.save(user);
        userId = user.getId();

        CartRequest request = CartRequest.builder()
                .productId(productId)
                .quantity(1)
                .price(3000)
                .checkoutMethod("card")
                .build();

        cartItemId = cartService.addToCart(userId, request);
    }

    @AfterEach
    void deleteRepositories() {
        cartRepository.deleteAll();
        priceCategoryRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void addToCart() {
        CartRequest request = CartRequest.builder()
                .productId(productId)
                .quantity(2)
                .price(2000)
                .checkoutMethod("card")
                .build();

        Long newCartItemId = cartService.addToCart(userId, request);
        assertThat(newCartItemId).isNotNull();
    }

    @Test
    void getCartItems() {
        List<CartResponse> cartItems = cartService.getCartItems(userId);
        assertThat(cartItems).hasSize(1);
        assertThat(cartItems.get(0).getName()).isEqualTo("문화상품권");
    }

    @Test
    void updateCartItem() {
        cartService.updateCartItem(cartItemId, 5);
        CartResponse cartItem = cartService.getCartItems(userId).get(0);
        assertThat(cartItem.getQuantity()).isEqualTo(5);
    }

    @Test
    void deleteCartItem() {
        cartService.deleteCartItem(cartItemId);
        List<CartResponse> cartItems = cartService.getCartItems(userId);
        assertThat(cartItems).isEmpty();
    }

}