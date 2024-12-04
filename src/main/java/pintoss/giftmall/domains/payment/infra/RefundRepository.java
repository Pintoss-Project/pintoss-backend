package pintoss.giftmall.domains.payment.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.payment.domain.Refund;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund,Long> {
}
