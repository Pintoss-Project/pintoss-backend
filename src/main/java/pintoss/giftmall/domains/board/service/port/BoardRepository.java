package pintoss.giftmall.domains.board.service.port;

import pintoss.giftmall.domains.board.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    List<Board> findAll();
    Optional<Board> findById(Long id);

    Board register(Board board);

    Board update(Board board);

    void deletedById(Long id);

}
