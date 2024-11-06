package pintoss.giftmall.domains.payment.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.payment.dto.PaymentRequest;
import pintoss.giftmall.domains.payment.dto.PaymentResponse;
import pintoss.giftmall.domains.payment.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<PaymentResponse> processPayment(@RequestBody @Valid PaymentRequest request, @RequestParam @NotNull Long userId, @RequestParam @NotNull Long orderId) {
        PaymentResponse paymentResponse = paymentService.processPayment(userId, orderId, request);
        return ApiResponse.ok(paymentResponse);
    }

    @GetMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> getPayment(@PathVariable(value = "paymentId") Long paymentId) {
        PaymentResponse payment = paymentService.getPayment(paymentId);
        return ApiResponse.ok(payment);
    }

    @PostMapping("/{paymentId}/cancel")
    public ApiResponse<String> cancelPayment(@PathVariable(value = "paymentId") Long paymentId) {
        paymentService.cancelPayment(paymentId);
        return ApiResponse.ok("결제가 취소되었습니다.");
    }

    @PostMapping("/{paymentId}/refund")
    public ApiResponse<String> refundPayment(@PathVariable(value = "paymentId") Long paymentId) {
        paymentService.refundPayment(paymentId);
        return ApiResponse.ok("결제 금액이 환불되었습니다.");
    }

}
