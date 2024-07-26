package pintoss.giftmall.domains.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.dto.OrderRequest;
import pintoss.giftmall.domains.order.dto.OrderResponse;
import pintoss.giftmall.domains.order.service.OrderService;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.service.PaymentService;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.findAllByUserId(userId);
        return ApiResponse.ok(orders);
    }

    @PostMapping("/{userId}")
    public ApiResponse<Long> createOrder(@PathVariable Long userId, @RequestBody OrderRequest requestDTO, @RequestBody PaymentRequest paymentRequest) {
        Long paymentId = paymentService.processPaymentFromCart(paymentRequest, userId);
        Payment payment = paymentService.getPayment(paymentId).toEntity(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다.")));

        Long orderId = orderService.createOrder(userId, requestDTO, payment);
        return ApiResponse.ok(orderId);
    }

}
