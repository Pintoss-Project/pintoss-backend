package pintoss.giftmall.domains.payment.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.payment.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByOrderId(Long orderId);

}
