package pintoss.giftmall.domains.payment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime refundDate;

    @Column(length = 50, nullable = false)
    private String transactionId;

    @Column(length = 10)
    private String responseCode;

    @Column(length = 255)
    private String responseMessage;

    @Column(length = 10)
    private String detailResponseCode;

    @Column(length = 255)
    private String detailResponseMessage;

    private BigDecimal cancelAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private RenewPayment payment;

}
