package pintoss.giftmall.domains.order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.enums.UserRole;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.dto.OrderRequest;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.payment.service.PaymentService;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Long userId;
    private Long orderId;

    @BeforeEach
    void setUp() {
        user = new User("user@example.com", "password", "유저1", "010-1234-5678", UserRole.USER);
        userRepository.save(user);
        userId = user.getId();
    }

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("주문서 생성 성공 테스트")
    void testCreateOrder() {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderNo("1234567-1234567")
                .orderPrice(10000)
                .orderStatus("주문완료")
                .payMethod("card")
                .build();
        orderId = orderService.createOrder(userId, orderRequest);

        Order createdOrder = orderService.findById(orderId);
        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getUser().getName()).isEqualTo("유저1");

        this.orderId = orderId;
    }

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("결제 성공 테스트")
    void testProcessPaymentSuccess() {
        testCreateOrder();

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .payStatus("결제요청")
                .payMethod("card")
                .payPrice(10000)
                .build();

        PaymentResponse paymentResponse = paymentService.processPayment(userId, orderId, paymentRequest);
        assertThat(paymentResponse).isNotNull();
        assertThat(paymentResponse.getPayStatus()).isEqualTo("success");

        PaymentResponse paymentInfo = paymentService.getPayment(paymentResponse.getId());
        assertThat(paymentInfo).isNotNull();
        assertThat(paymentInfo.getPayPrice()).isEqualTo(10000);
    }

    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("결제 실패 테스트")
    void testProcessPaymentFailure() {
        testCreateOrder();

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .payStatus("결제요청")
                .payMethod("card")
                .payPrice(10000)
                .build();

        PaymentResponse paymentResponse = paymentService.processPayment(userId, orderId, paymentRequest);
        assertThat(paymentResponse).isNotNull();
        assertThat(paymentResponse.getPayStatus()).isEqualTo("success");
    }

}