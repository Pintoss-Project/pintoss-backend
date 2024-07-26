package pintoss.giftmall.domains.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.order.domain.Order;
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
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;
    @Transactional
    public Long processPaymentFromCart(PaymentRequest paymentRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        Payment payment = paymentRequest.toEntity(null, user);
        paymentRepository.save(payment);

        Order order = orderService.createOrder(userId, paymentRequest.toOrderRequest());
        updateOrderForPayment(payment.getId(), order);

        return payment.getId();
    }

    @Transactional
    public Long processPaymentFromProduct(PaymentRequest paymentRequest, Long userId, Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        Payment payment = paymentRequest.toEntity(null, user);
        paymentRepository.save(payment);

        Order order = orderService.createOrder(userId, paymentRequest.toOrderRequest());
        updateOrderForPayment(payment.getId(), order);

        return payment.getId();
    }


    @Transactional
    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        return PaymentResponse.fromEntity(payment);
    }

    @Transactional
    public void cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        paymentRepository.delete(payment);
    }

    @Transactional
    public void refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        payment.refund();
        paymentRepository.save(payment);
    }

    @Transactional
    public void updateOrderForPayment(Long paymentId, Order order) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
        payment.assignOrder(order);
        paymentRepository.save(payment);
    }

}
