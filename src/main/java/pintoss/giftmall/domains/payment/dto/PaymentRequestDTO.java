package pintoss.giftmall.domains.payment.dto;

import lombok.*;
import pintoss.giftmall.common.enums.PayMethod;
import pintoss.giftmall.common.enums.PayStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    private String serviceId;
    private String serviceCode;
    private String orderId;
    private String orderDate;
    private String itemName;
    private PayMethod payMethod;
    private PayStatus payStatus;
    private BigDecimal payPrice;
    private String amount;
    private String checksum;
}
