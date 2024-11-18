package pintoss.giftmall.domains.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundRequestDTO {
    private String transactionId;
    private BigDecimal cancelAmount;
    private String responseCode;
    private String responseMessage;
    private String cancelType;    // 환불 타입 (부분 환불 여부)

}
