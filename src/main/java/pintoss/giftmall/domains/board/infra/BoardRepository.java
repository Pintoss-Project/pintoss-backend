package pintoss.giftmall.domains.board.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
