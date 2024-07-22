package pintoss.giftmall.domains.board.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.board.domain.Board;
import pintoss.giftmall.domains.board.service.port.BoardRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public List<Board> findAll() {
        return boardJpaRepository.findAll();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardJpaRepository.findById(id);
    }

    @Override
    public Board register(Board board) {
        return boardJpaRepository.save(board);
    }

    @Override
    public Board update(Board board) {
        return boardJpaRepository.save(board);
    }

    @Override
    public void deletedById(Long id) {
        boardJpaRepository.deleteById(id);
    }

}
