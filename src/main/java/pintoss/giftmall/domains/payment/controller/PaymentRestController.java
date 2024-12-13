package pintoss.giftmall.domains.payment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.payment.service.PaymentServiceV2;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payment")
public class PaymentRestController {

    private final PaymentServiceV2 paymentService;

    // 결제 승인
    @PostMapping("/approve")
    public ResponseEntity<PaymentResponse> approvePayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.processPayment(paymentRequest.getUserId(), paymentRequest.getOrderId(), paymentRequest);
        return ResponseEntity.ok(response);
    }

    // 결제 취소
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelPayment(@RequestParam Long paymentId) {
        paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok("결제가 취소되었습니다.");
    }

    // 부분 취소
    @PostMapping("/partial-cancel")
    public ResponseEntity<String> partialCancelPayment(
            @RequestParam Long paymentId,
            @RequestParam BigDecimal cancelAmount,
            @RequestParam(required = false) String cancelReason) {
        paymentService.partialCancelPayment(paymentId, cancelAmount, cancelReason);
        return ResponseEntity.ok("부분 결제가 성공적으로 처리되었습니다.");
    }

    // 환불
    @PostMapping("/refund")
    public ResponseEntity<String> refundPayment(@RequestParam Long paymentId) {
        paymentService.refundPayment(paymentId);
        return ResponseEntity.ok("결제가 환불되었습니다.");
    }

    // 결제 결과 콜백 처리(승인 + 취소)
    @PostMapping("/callback")
    public ResponseEntity<String> handlePaymentCallback(@RequestParam Map<String, String> params) {
        try {
            paymentService.handleCallback(params);
            return ResponseEntity.ok("콜백 처리 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("콜백 처리 실패: " + e.getMessage());
        }
    }

    // 결제 결과 조회(transactionId로 조회)
    @GetMapping("/{Id}")
    public ResponseEntity<?> getPaymentByTransactionId(@PathVariable(value = "Id") Long paymentId) {
        try{
            PaymentResponse payment = paymentService.getPayment(paymentId);
            return ResponseEntity.status(HttpStatus.OK).body(payment);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 조회 실패: " + e.getMessage());
        }
    }
}
