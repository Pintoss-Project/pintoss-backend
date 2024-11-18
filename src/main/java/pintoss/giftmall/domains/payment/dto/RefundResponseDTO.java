package pintoss.giftmall.domains.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RefundResponseDTO {
    private Long refundId;
    private String transactionId;
    private BigDecimal cancelAmount;
    private String responseCode;
    private String responseMessage;
    private LocalDateTime refundDate;
}
