package pintoss.giftmall.domains.payment.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequestDTO {
    private String transactionId;
    private BigDecimal cancelAmount;
    private String responseCode;
    private String responseMessage;
    private String cancelType;
    private String serviceId;
    private String serviceCode;
    private String orderDate;
    private String orderId;
    private String cancelurl;
}
