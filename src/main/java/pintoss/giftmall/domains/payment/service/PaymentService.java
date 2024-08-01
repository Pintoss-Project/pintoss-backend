package pintoss.giftmall.domains.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.cart.infra.CartRepository;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.infra.PaymentReader;
import pintoss.giftmall.domains.payment.infra.PaymentRepository;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.domain.OrderProduct;
import pintoss.giftmall.domains.order.infra.OrderProductRepository;
import pintoss.giftmall.domains.order.infra.OrderReader;
import pintoss.giftmall.domains.order.infra.OrderRepository;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.infra.PriceCategoryReader;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserReader;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserReader userReader;
    private final OrderReader orderReader;
    private final PaymentReader paymentReader;
    private final PriceCategoryReader priceCategoryReader;

    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public PaymentResponse processPayment(Long userId, Long orderId, PaymentRequest paymentRequest) {
        User user = userReader.findById(userId);
        Order order = orderReader.findById(orderId);

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
        Payment payment = paymentReader.findById(paymentId);

        return PaymentResponse.fromEntity(payment);
    }

    public void cancelPayment(Long paymentId) {
        Payment payment = paymentReader.findById(paymentId);
        paymentRepository.delete(payment);
    }

    public void refundPayment(Long paymentId) {
        Payment payment = paymentReader.findById(paymentId);
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
            PriceCategory priceCategory = priceCategoryReader.findById(orderProduct.getPriceCategoryId());
            priceCategory.decreaseStock(orderProduct.getQuantity());
        });
    }
}
