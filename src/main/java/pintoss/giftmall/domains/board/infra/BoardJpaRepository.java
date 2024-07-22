package pintoss.giftmall.domains.board.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.board.domain.Board;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {
}
