package pintoss.giftmall.domains.order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.dto.OrderRequest;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    private Long userId;

    @BeforeEach
    void setUp() {
        User user = new User("user@example.com", "password", "유저1", "010-1234-5678");
        userRepository.save(user);
        userId = user.getId();
    }

    @Test
    void createOrder() {
        OrderRequest request = OrderRequest.builder()
                .orderNo("1234567-1234567")
                .orderPrice(10000)
                .orderStatus("주문완료")
                .payMethod("card")
                .isSent(false)
                .build();

        Order order = orderService.createOrder(userId, request);
        Order createdOrder = orderService.findById(order.getId());

        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getUser().getName()).isEqualTo("유저1");
    }

}