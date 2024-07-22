package pintoss.giftmall.domains.board.service.port;

import pintoss.giftmall.domains.board.domain.BoardImage;

import java.util.List;

public interface BoardImageRepository {

    void save(BoardImage boardImage);

    List<BoardImage> findAllByBoardId(Long boardId);

    void delete(BoardImage boardImage);

    void saveAll(List<BoardImage> boardImages);

}
