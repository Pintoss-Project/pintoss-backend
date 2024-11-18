package pintoss.giftmall.domains.payment.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.payment.domain.RenewPayment;

import java.util.List;
import java.util.Optional;

public interface RenewPaymentRepository extends JpaRepository<RenewPayment,Long> {

    Optional<RenewPayment> findByTransactionId(String transactionId);
}
