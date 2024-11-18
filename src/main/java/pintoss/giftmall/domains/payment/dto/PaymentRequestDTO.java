package pintoss.giftmall.domains.payment.dto;

import lombok.Data;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.common.enums.PayStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentRequestDTO {
    private String serviceId;
    private String orderId;
    private LocalDateTime orderDate;
    private String transactionId;
    private PayMethod payMethod;
    private PayStatus payStatus;
    private BigDecimal payPrice;
}
