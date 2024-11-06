package pintoss.giftmall.domains.board.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.board.domain.Board;
import pintoss.giftmall.domains.board.domain.BoardImage;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

    List<BoardImage> findAllByBoardId(Long boardId);

    List<BoardImage> findByBoard(Board board);

    void deleteByBoard(Board board);
}
