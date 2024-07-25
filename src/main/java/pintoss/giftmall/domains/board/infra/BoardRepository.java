package pintoss.giftmall.domains.board.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.board.domain.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByType(String type);

}
