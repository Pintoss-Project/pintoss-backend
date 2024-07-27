package pintoss.giftmall.domains.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.board.domain.Board;
import pintoss.giftmall.domains.board.dto.BoardRequest;
import pintoss.giftmall.domains.board.dto.BoardResponse;
import pintoss.giftmall.domains.board.infra.BoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponse> findAllByType(String type) {
        return boardRepository.findByType(type).stream()
                .map(BoardResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardResponse findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("board_id : " + id));
        return BoardResponse.fromEntity(board);
    }

    public Long create(BoardRequest requestDTO) {
        Board board = requestDTO.toEntity();
        boardRepository.save(board);
        return board.getId();
    }

    public Long update(Long id, BoardRequest requestDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("board_id : " + id));

        board.update(requestDTO);

        return board.getId();
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

}
