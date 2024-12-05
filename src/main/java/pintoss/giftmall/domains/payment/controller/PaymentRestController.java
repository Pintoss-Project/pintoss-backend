package pintoss.giftmall.domains.payment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.payment.service.PaymentServiceV2;

@RestController
@AllArgsConstructor
public class PaymentRestController {

    private final PaymentServiceV2 paymentServiceV2;

    /**
     * 결제 처리
     *
     * @param userId 사용자 ID
     * @param orderId 주문 ID
     * @param paymentRequest 결제 요청 정보
     * @return PaymentResponse
     */
    @PostMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "orderId") Long orderId,
            @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentServiceV2.processPayment(userId, orderId, paymentRequest);
        return ResponseEntity.ok(paymentResponse);
    }

    /**
     * 결제 조회
     *
     * @param paymentId 결제 ID
     * @return PaymentResponse
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable(value = "paymentId") Long paymentId) {
        PaymentResponse paymentResponse = paymentServiceV2.getPayment(paymentId);
        return ResponseEntity.ok(paymentResponse);
    }

    /**
     * 결제 취소
     *
     * @param paymentId 결제 ID
     * @return 성공 메시지
     */
    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<String> cancelPayment(@PathVariable(value = "paymentId") Long paymentId) {
        paymentServiceV2.cancelPayment(paymentId);
        return ResponseEntity.ok("결제가 성공적으로 취소되었습니다.");
    }

    /**
     * 결제 환불
     *
     * @param paymentId 결제 ID
     * @return 성공 메시지
     */
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<String> refundPayment(@PathVariable(value = "paymentId") Long paymentId) {
        paymentServiceV2.refundPayment(paymentId);
        return ResponseEntity.ok("결제가 성공적으로 환불되었습니다.");
    }
}
