package pintoss.giftmall.domains.payment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.dto.OrderProductRequest;
import pintoss.giftmall.domains.order.dto.OrderRequest;
import pintoss.giftmall.domains.order.infra.OrderRepository;
import pintoss.giftmall.domains.order.service.OrderService;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.payment.infra.PaymentRepository;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceCategoryRepository priceCategoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentRepository paymentRepository;

    private User user;
    private Product product;
    private PriceCategory priceCategory;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("testuser@example.com", "123456789", "testuser", "010-1234-5678"));
        product = productRepository.save(Product.builder()
                .name("문화상품권")
                .isPopular(true)
                .cardDiscount(BigDecimal.valueOf(1.2))
                .phoneDiscount(BigDecimal.valueOf(0))
                .homePage("http://testproduct.com")
                .csCenter("1544-6789")
                .description("상셋설명")
                .publisher("문화")
                .category("cbm")
                .build());
        priceCategory = priceCategoryRepository.save(new PriceCategory("문화상품권 3000원권", 3000, 50, product));
    }

    @Test
    void testOrderSuccess() {
        OrderProductRequest orderProductRequest = OrderProductRequest.builder()
                .productId(product.getId())
                .priceCategoryId(priceCategory.getId())
                .quantity(10)
                .price(3000)
                .build();

        OrderRequest orderRequest = OrderRequest.builder()
                .orderNo("1234567-1234567")
                .orderPrice(30000)
                .orderStatus("success")
                .payMethod("card")
                .orderProducts(Collections.singletonList(orderProductRequest))
                .build();

        Long orderId = orderService.createOrder(user.getId(), orderRequest);

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .payPrice(30000)
                .payMethod("card")
                .build();

        PaymentResponse paymentResponse = paymentService.processPayment(user.getId(), orderId, paymentRequest);

        assertNotNull(paymentResponse);
        assertEquals("success", paymentResponse.getPayStatus());

        Order order = orderRepository.findById(orderId).orElseThrow();
        assertEquals("success", order.getPayStatus());

        PriceCategory updatedPriceCategory = priceCategoryRepository.findById(priceCategory.getId()).orElseThrow();
        assertEquals(40, updatedPriceCategory.getStock());

    }

    @Test
    void testOrderFailure() {
        OrderProductRequest orderProductRequest = OrderProductRequest.builder()
                .productId(product.getId())
                .priceCategoryId(priceCategory.getId())
                .quantity(10)
                .price(3000)
                .build();

        OrderRequest orderRequest = OrderRequest.builder()
                .orderNo("1234567-1234567")
                .orderPrice(30000)
                .orderStatus("success")
                .payMethod("card")
                .orderProducts(Collections.singletonList(orderProductRequest))
                .build();

        Long orderId = orderService.createOrder(user.getId(), orderRequest);

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .payPrice(30000)
                .payMethod("card")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment(user.getId(), orderId, paymentRequest);
        });

        Order order = orderRepository.findById(orderId).orElseThrow();
        assertEquals("fail", order.getPayStatus());

        PriceCategory updatePriceCategory = priceCategoryRepository.findById(priceCategory.getId()).orElseThrow();
        assertEquals(50, updatePriceCategory.getStock());

    }

}
