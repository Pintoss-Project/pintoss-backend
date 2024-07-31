package pintoss.giftmall.domains.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.cart.infra.CartRepository;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.domain.OrderProduct;
import pintoss.giftmall.domains.order.infra.OrderProductRepository;
import pintoss.giftmall.domains.order.infra.OrderRepository;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.payment.infra.PaymentRepository;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.service.PriceCategoryService;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;
    private final PriceCategoryRepository priceCategoryRepository;

    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public PaymentResponse processPayment(Long userId, Long orderId, PaymentRequest paymentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "유저 id를 다시 확인해주세요."));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "주문 id를 다시 확인해주세요."));

        String externalPayStatus = callExternalPaymentApi(paymentRequest);

        Payment payment = paymentRequest.toEntity(user, order);

        if ("success".equals(externalPayStatus)) {
            paymentRepository.save(payment);
            payment.completePayment();
            order.updatePayStatus(payment.getPayStatus());
            handleOrderSuccess(order);
            return PaymentResponse.fromEntity(payment);
        } else {
            payment.failPayment();
            updateOrderStatusAndDeleteCartItems(order, payment.getPayStatus(), user);
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST, "결제가 실패했습니다.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOrderStatusAndDeleteCartItems(Order order, String payStatus, User user) {
        order.updatePayStatus(payStatus);
        orderRepository.save(order);
        orderRepository.flush();
        cartRepository.deleteAllByUser(user);
    }

    private String callExternalPaymentApi(PaymentRequest paymentRequest) {
        return "fail";
    }

    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "결제 id를 다시 확인해주세요."));
        return PaymentResponse.fromEntity(payment);
    }

    public void cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "결제 id를 다시 확인해주세요."));
        paymentRepository.delete(payment);
    }

    public void refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "결제 id를 다시 확인해주세요."));
        payment.refund();
    }

    private void handleOrderSuccess(Order order) {
        subtractStock(order);
        if (order.isOrderInCart()) {
            deleteCartItems(order.getUser());
        }
    }

    private void deleteCartItems(User user) {
        cartRepository.deleteAllByUser(user);
    }

    private void subtractStock(Order order) {
        List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(order.getId());
        orderProducts.forEach(orderProduct -> {
            PriceCategory priceCategory = priceCategoryRepository.findById(orderProduct.getPriceCategoryId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "가격 카테고리 id를 다시 확인해주세요."));
            priceCategory.decreaseStock(orderProduct.getQuantity());
        });
    }
}
