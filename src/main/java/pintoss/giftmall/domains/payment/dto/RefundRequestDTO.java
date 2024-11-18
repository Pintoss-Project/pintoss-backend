package pintoss.giftmall.domains.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundRequestDTO {
    private String transactionId;
    private BigDecimal cancelAmount;
    private String responseCode;
    private String responseMessage;
    private String cancelType;    // ȯ�� Ÿ�� (�κ� ȯ�� ����)

}
