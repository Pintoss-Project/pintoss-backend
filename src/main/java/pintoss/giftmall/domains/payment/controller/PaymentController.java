package pintoss.giftmall.domains.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.payment.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<Long> processPayment(@RequestBody PaymentRequest requestDTO) {
        Long paymentId = paymentService.processPayment(requestDTO);
        return ApiResponse.ok(paymentId);
    }

    @GetMapping("/{paymentId")
    public ApiResponse<PaymentResponse> getPayment(@PathVariable Long paymentId) {
        PaymentResponse payment = paymentService.getPayment(paymentId);
        return ApiResponse.ok(payment);
    }

    @PostMapping("/{paymentId}/cancel")
    public ApiResponse<String> cancelPayment(@PathVariable Long paymentId) {
        paymentService.cancelPayment(paymentId);
        return ApiResponse.ok("결제가 취소되었습니다.");
    }

    @PostMapping("/{paymentId}/refund")
    public ApiResponse<String> refundPayment(@PathVariable Long paymentId) {
        paymentService.refundPayment(paymentId);
        return ApiResponse.ok("결제 금액이 환불되었습니다.");
    }

}
