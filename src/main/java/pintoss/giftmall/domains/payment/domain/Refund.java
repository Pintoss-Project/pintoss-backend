package pintoss.giftmall.domains.payment.domain;

import jakarta.persistence.*;
import lombok.*;
import pintoss.giftmall.common.enums.RefundResponseCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "refund")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    private Long id;
    // 환불일
    @Column(nullable = false)
    private LocalDateTime refundDate;
    // 트랜잭션 ID
    @Column(length = 50, nullable = false)
    private String transactionId;
    // 응답 코드
    @Column(length = 10)
    private String responseCode;
    // 응답 메시지
    @Column(length = 255)
    private String responseMessage;
    // 상세 응답 코드
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private RefundResponseCode detailResponseCode;
    // 상세 응답 메시지
    @Column(length = 255)
    private String detailResponseMessage;
    // 환불 금액
    private BigDecimal cancelAmount;
    // 연관된 내용.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private RenewPayment payment;

}
