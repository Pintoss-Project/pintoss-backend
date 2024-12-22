package pintoss.giftmall.domains.board.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.common.enums.BoardType;
import pintoss.giftmall.domains.board.domain.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

    List<Board> findByType(BoardType type);

}
