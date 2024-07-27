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

    @GetMapping("/{userId}")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.findAllByUserId(userId);
        return ApiResponse.ok(orders);
    }

    @PostMapping("/{userId}")
    public ApiResponse<Long> createOrder(@PathVariable Long userId, @RequestBody OrderRequest requestDTO) {
        Long orderId = orderService.createOrder(userId, requestDTO);
        return ApiResponse.ok(orderId);
    }

}
