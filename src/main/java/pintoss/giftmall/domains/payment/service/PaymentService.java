package pintoss.giftmall.domains.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.dto.OrderRequest;
import pintoss.giftmall.domains.order.infra.OrderRepository;
import pintoss.giftmall.domains.order.service.OrderService;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.payment.infra.PaymentRepository;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long processPayment(Long userId, Long orderId, PaymentRequest paymentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        Payment payment = paymentRequest.toEntity(user, order);
        paymentRepository.save(payment);

        return payment.getId();
    }

    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        return PaymentResponse.fromEntity(payment);
    }

    public void cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        paymentRepository.delete(payment);
    }

    public void refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        payment.refund();
    }

}
