package pintoss.giftmall.domains.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.board.domain.Board;
import pintoss.giftmall.domains.board.dto.BoardRequest;
import pintoss.giftmall.domains.board.dto.BoardResponse;
import pintoss.giftmall.domains.board.infra.BoardRepository;
import pintoss.giftmall.domains.board.infra.BoardReader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardReader boardReader;

    @Transactional(readOnly = true)
    public List<BoardResponse> findAllByType(String type) {
        if (!StringUtils.hasText(type)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST, "타입을 다시 확인해주세요. type : " + type);
        }
        return boardRepository.findByType(type).stream()
                .map(BoardResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardResponse findById(Long id) {
        Board board = boardReader.findById(id);
        return BoardResponse.fromEntity(board);
    }

    public Long create(BoardRequest requestDTO) {
        Board board = requestDTO.toEntity();
        boardRepository.save(board);
        return board.getId();
    }

    public Long update(Long id, BoardRequest requestDTO) {
        Board board = boardReader.findById(id);

        board.update(requestDTO);
        return board.getId();
    }

    public void delete(Long id) {
        Board board = boardReader.findById(id);

        boardRepository.delete(board);
    }

}
