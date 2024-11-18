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
     * ���� ���� ��û
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO dto) {
        try {
            var payment = renewPaymentService.savePayment(dto);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            log.error("���� ���� �� ���� �߻�", e);
            return ResponseEntity.internalServerError().body("���� ���� ����: " + e.getMessage());
        }
    }

    /**
     * ���� ���� ��û
     */
    @PostMapping("/approve")
    public ResponseEntity<?> approvePayment(@RequestBody Map<String, String> authInfo) {
        try {
            Message response = renewPaymentService.MessageAuthProcess(authInfo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("���� ���� �� ���� �߻�", e);
            return ResponseEntity.internalServerError().body("���� ���� ����: " + e.getMessage());
        }
    }

    /**
     * ȯ�� ó��
     */
    @PostMapping("/refund")
    public ResponseEntity<?> processRefund(@RequestBody RefundRequestDTO dto) {
        try {
            Refund refund;
            if (dto.getCancelAmount() != null) {
                // �κ� ȯ��
                refund = renewPaymentService.processPartialRefund(dto);
            } else {
                // ��ü ȯ��
                refund = renewPaymentService.processFullRefund(dto);
            }
            return ResponseEntity.ok(refund);
        } catch (Exception e) {
            log.error("ȯ�� ó�� �� ���� �߻�", e);
            return ResponseEntity.internalServerError().body("ȯ�� ó�� ����: " + e.getMessage());
        }
    }

    /**
     * üũ�� ���� ��û
     */
    @PostMapping("/checksum")
    public ResponseEntity<?> generateCheckSum(@RequestParam String input) {
        try {
            String checksum = com.galaxia.api.util.ChecksumUtil.genCheckSum(input);
            return ResponseEntity.ok(Map.of("checksum", checksum));
        } catch (Exception e) {
            log.error("üũ�� ���� �� ���� �߻�", e);
            return ResponseEntity.internalServerError().body("üũ�� ���� ����: " + e.getMessage());
        }
    }
}
