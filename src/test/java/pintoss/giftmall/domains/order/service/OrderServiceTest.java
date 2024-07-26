package pintoss.giftmall.domains.order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.dto.OrderRequest;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.service.PaymentService;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Long userId;

    @BeforeEach
    void setUp() {
        user = new User("user@example.com", "password", "유저1", "010-1234-5678");
        userRepository.save(user);
        userId = user.getId();
    }

    @Test
    @Transactional
    @Rollback(false)
    void testCreateOrder() {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .payStatus("완료")
                .payMethod("card")
                .totalPrice(10000)
                .discountPrice(1000)
                .build();

        OrderRequest orderRequest = paymentRequest.toOrderRequest("1234567-1234567", "주문완료", false);
        Long orderId = orderService.createOrder(userId, orderRequest);

        Order createdOrder = orderService.findById(orderId);
        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getUser().getName()).isEqualTo("유저1");
    }

}