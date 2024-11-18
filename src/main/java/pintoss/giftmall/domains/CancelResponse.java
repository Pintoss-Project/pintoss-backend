package pintoss.giftmall.domains;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelResponse {

    private String responseCode;
    private String responseMessage;
    private String detailResponseCode;
    private String detailResponseMessage;
    private String transactionId;
    private String cancelAmount;
}
