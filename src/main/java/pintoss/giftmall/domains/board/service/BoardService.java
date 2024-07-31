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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

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
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "게시판 id를 다시 확인해주세요."));
        return BoardResponse.fromEntity(board);
    }

    public Long create(BoardRequest requestDTO) {
        try {
            Board board = requestDTO.toEntity();
            boardRepository.save(board);
            return board.getId();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "게시판", ErrorCode.CREATION_FAILURE);
        }
    }

    public Long update(Long id, BoardRequest requestDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "게시판 id를 다시 확인해주세요."));

        try {
            board.update(requestDTO);
            return board.getId();
        } catch(Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "게시판", ErrorCode.UPDATE_FAILURE);
        }
    }

    public void delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "게시판 id를 다시 확인해주세요."));

        try {
            boardRepository.delete(board);
        } catch(Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "게시판", ErrorCode.DELETION_FAILURE);
        }
    }

}
