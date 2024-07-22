package pintoss.giftmall.domains.board.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.board.domain.BoardImage;
import pintoss.giftmall.domains.board.service.port.BoardImageRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardImageRepositoryImpl implements BoardImageRepository {

    private final BoardImageJpaRepository boardImageJpaRepository;

    @Override
    public void save(BoardImage boardImage) {
        boardImageJpaRepository.save(boardImage);
    }

    @Override
    public List<BoardImage> findAllByBoardId(Long boardId) {
        return boardImageJpaRepository.findAllByBoardId(boardId);
    }

    @Override
    public void delete(BoardImage boardImage) {
        boardImageJpaRepository.delete(boardImage);
    }

    @Override
    public void saveAll(List<BoardImage> boardImages) {
        boardImageJpaRepository.saveAll(boardImages);
    }

}
