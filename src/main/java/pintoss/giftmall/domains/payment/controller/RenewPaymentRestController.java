package pintoss.giftmall.domains.payment.controller;

import com.galaxia.api.merchant.Message;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/api/payment")
public class RenewPaymentRestController {

    private final RenewPaymentService renewPaymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO dto) {
        try {
            RenewPayment renewPayment = renewPaymentService.savePayment(dto);
            return ResponseEntity.ok(renewPayment);
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.internalServerError().body("fail::" + e.getMessage());
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approvePayment(@RequestBody Map<String, String> authInfo) {
        try {
            Message response = renewPaymentService.MessageAuthProcess(authInfo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.internalServerError().body("fail::" + e.getMessage());
        }
    }

    @PostMapping("/createAndApprove")
    public ResponseEntity<?> createAndApprovePayment(@RequestBody PaymentRequestDTO dto) {
        try {
            RenewPayment payment = renewPaymentService.approveAndSavePayment(dto);
            return ResponseEntity.ok(payment);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/refund")
    public ResponseEntity<?> processRefund(@RequestBody RefundRequestDTO dto) {
        try {
            Refund refund;
            if (dto.getCancelAmount() != null) {
                refund = renewPaymentService.processPartialRefund(dto);
            } else {
                refund = renewPaymentService.processFullRefund(dto);
            }
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("fail::" + e.getMessage());
        }
    }

    @PostMapping("/checksum")
    public ResponseEntity<?> generateCheckSum(@RequestParam(value = "CheckSum",required = false) String input) {
        try {
            String checksum = com.galaxia.api.util.ChecksumUtil.genCheckSum(input);
            return ResponseEntity.ok(checksum);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("fail::" + e.getMessage());
        }
    }

}
