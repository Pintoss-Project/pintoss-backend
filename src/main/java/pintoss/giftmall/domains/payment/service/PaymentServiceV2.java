package pintoss.giftmall.domains.payment.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import pintoss.giftmall.common.enums.PayStatus;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.cart.infra.CartRepository;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.domain.OrderProduct;
import pintoss.giftmall.domains.order.infra.OrderProductRepository;
import pintoss.giftmall.domains.order.infra.OrderReader;
import pintoss.giftmall.domains.order.infra.OrderRepository;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.payment.infra.PaymentReader;
import pintoss.giftmall.domains.payment.infra.PaymentRepository;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.infra.PriceCategoryReader;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PaymentServiceV2 {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserReader userReader;
    private final OrderReader orderReader;
    private final PaymentReader paymentReader;
    private final PriceCategoryReader priceCategoryReader;
    private final BillgateService billgateService;

    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public PaymentResponse processPayment(Long userId, Long orderId, PaymentRequest paymentRequest) {
        User user = userReader.findById(userId);
        Order order = orderReader.findById(orderId);

        // Billgate 결제 승인 요청
        try {
            billgateService.approvePayment(
                    paymentRequest.getTransactionId(),
                    paymentRequest.getAmount(),
                    paymentRequest.getPayMethod()
            );
        } catch (HttpClientErrorException e) {
            // 결제 실패 처리
            Payment failedPayment = paymentRequest.toEntity(user, order);
            failedPayment.failPayment();
            updateOrderStatusAndDeleteCartItems(order, failedPayment.getPayStatus(), user);
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST, "결제가 실패했습니다.");
        }

        // 결제 성공 처리
        Payment payment = paymentRequest.toEntity(user, order);
        payment.setPayStatus(PayStatus.PENDING);//승인 대기 -> 콜백 처리이후 승인으로 변경하기.
        paymentRepository.save(payment);
        order.updatePayStatus(payment.getPayStatus());
        handleOrderSuccess(order);
        return PaymentResponse.fromEntity(payment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateOrderStatusAndDeleteCartItems(Order order, PayStatus payStatus, User user) {
        order.updatePayStatus(payStatus);
        orderRepository.save(order);
        orderRepository.flush();
        cartRepository.deleteAllByUser(user);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentReader.findById(paymentId);
        return PaymentResponse.fromEntity(payment);
    }

    public void cancelPayment(Long paymentId) {
        Payment payment = paymentReader.findById(paymentId);
        billgateService.cancelPayment(payment.getTransactionId(), payment.getPayMethod());
        paymentRepository.delete(payment);
    }

    public void refundPayment(Long paymentId) {
        Payment payment = paymentReader.findById(paymentId);
        billgateService.cancelPayment(payment.getTransactionId(), payment.getPayMethod());
        payment.refund();
    }

    public void partialCancelPayment(Long paymentId, BigDecimal cancelAmount, String cancelReason) {
        Payment payment = paymentReader.findById(paymentId);

        // Billgate 부분 취소 요청
        billgateService.partialCancelPayment(payment.getTransactionId(), cancelAmount, payment.getPayMethod(), cancelReason);

        // 부분 취소 후 DB 반영 로직 (원래 결제 금액 업데이트)
        payment.updateAmountAfterPartialCancel(cancelAmount);
        paymentRepository.save(payment);
    }

    @Transactional
    public void handleCallback(Map<String, String> params) {
        String transactionId = params.get("TRANSACTION_ID");
        String responseCode = params.get("RESPONSE_CODE");
        String responseMessage = params.get("RESPONSE_MESSAGE");

        Payment payment = paymentReader.findByTransactionId(transactionId);
        System.out.println(params);
        System.out.println("transactionId::"+transactionId);
        System.out.println("responseMessage::"+responseMessage);
        System.out.println("responseCode::"+responseCode);

        if (payment == null) {
            throw new IllegalArgumentException("유효하지 않은 TRANSACTION_ID: " + transactionId);
        }

        if ("0000".equals(responseCode)) {
            payment.completePayment();
        } else {
            payment.failPayment();
        }

        paymentRepository.save(payment);
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
