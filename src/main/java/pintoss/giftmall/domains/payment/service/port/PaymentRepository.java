package pintoss.giftmall.domains.payment.service.port;

import pintoss.giftmall.domains.payment.domain.Payment;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);
    Optional<Payment> findByOrderId(Long orderId);

}
