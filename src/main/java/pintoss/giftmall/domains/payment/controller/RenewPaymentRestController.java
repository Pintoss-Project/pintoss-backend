package pintoss.giftmall.domains.payment.controller;

import com.galaxia.api.merchant.Message;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.domains.TestPaymentService;
import pintoss.giftmall.domains.payment.domain.Refund;
import pintoss.giftmall.domains.payment.domain.RenewPayment;
import pintoss.giftmall.domains.payment.dto.PaymentRequestDTO;
import pintoss.giftmall.domains.payment.dto.RefundRequestDTO;
import pintoss.giftmall.domains.payment.service.RenewPaymentService;

import java.util.Map;

@Log4j2
@RestController
@AllArgsConstructor
public class RenewPaymentRestController {

    private final RenewPaymentService renewPaymentService;

    /**
     * 결제 생성 요청
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO dto) {
        try {
            var payment = renewPaymentService.savePayment(dto);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            log.error("결제 생성 중 오류 발생", e);
            return ResponseEntity.internalServerError().body("결제 생성 실패: " + e.getMessage());
        }
    }

    /**
     * 결제 승인 요청
     */
    @PostMapping("/approve")
    public ResponseEntity<?> approvePayment(@RequestBody Map<String, String> authInfo) {
        try {
            Message response = renewPaymentService.MessageAuthProcess(authInfo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("결제 승인 중 오류 발생", e);
            return ResponseEntity.internalServerError().body("결제 승인 실패: " + e.getMessage());
        }
    }

    /**
     * 환불 처리
     */
    @PostMapping("/refund")
    public ResponseEntity<?> processRefund(@RequestBody RefundRequestDTO dto) {
        try {
            Refund refund;
            if (dto.getCancelAmount() != null) {
                // 부분 환불
                refund = renewPaymentService.processPartialRefund(dto);
            } else {
                // 전체 환불
                refund = renewPaymentService.processFullRefund(dto);
            }
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            log.error("환불 처리 중 오류 발생", e);
            return ResponseEntity.internalServerError().body("환불 처리 실패: " + e.getMessage());
        }
    }

    /**
     * 체크섬 생성 요청
     */
    @PostMapping("/checksum")
    public ResponseEntity<?> generateCheckSum(@RequestParam String input) {
        try {
            String checksum = com.galaxia.api.util.ChecksumUtil.genCheckSum(input);
            return ResponseEntity.ok(Map.of("checksum", checksum));
        } catch (Exception e) {
            log.error("체크섬 생성 중 오류 발생", e);
            return ResponseEntity.internalServerError().body("체크섬 생성 실패: " + e.getMessage());
        }
    }
}
