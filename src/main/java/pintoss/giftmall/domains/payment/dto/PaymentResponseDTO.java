package pintoss.giftmall.domains.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.common.enums.PayStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long paymentId;
    private String serviceId;
    private String orderId;
    private LocalDateTime orderDate;
    private String transactionId;
    private PayMethod payMethod;
    private BigDecimal payPrice;
    private PayStatus payStatus;
    private LocalDateTime approvedAt;
}
