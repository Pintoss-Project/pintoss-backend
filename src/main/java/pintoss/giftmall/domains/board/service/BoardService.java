package pintoss.giftmall.domains.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.enums.BoardType;
import pintoss.giftmall.common.exceptions.client.FieldMissingException;
import pintoss.giftmall.domains.board.domain.Board;
import pintoss.giftmall.domains.board.domain.BoardImage;
import pintoss.giftmall.domains.board.dto.BoardRequest;
import pintoss.giftmall.domains.board.dto.BoardResponse;
import pintoss.giftmall.domains.board.infra.BoardImageRepository;
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
    private final BoardImageRepository boardImageRepository;

    @Transactional(readOnly = true)
    public List<BoardResponse> findAllByType(BoardType type) {
        if (type == null) {
            throw new FieldMissingException("type");
        }

        return boardRepository.findAllByType(type);
    }

    @Transactional(readOnly = true)
    public BoardResponse findById(Long id) {
        Board board = boardReader.findById(id);
        List<String> imageUrls = boardImageRepository.findAllByBoardId(board.getId()).stream()
                .map(BoardImage::getUrl)
                .collect(Collectors.toList());
        return BoardResponse.fromEntity(board, imageUrls);
    }

    public Long create(BoardRequest requestDTO) {
        Board board = requestDTO.toEntity();
        boardRepository.save(board);

        if (requestDTO.getImageUrls() != null && !requestDTO.getImageUrls().isEmpty()) {
            saveBoardImages(requestDTO.getImageUrls(), board);
        }

        return board.getId();
    }

    public Long update(Long id, BoardRequest requestDTO) {
        Board board = boardReader.findById(id);
        board.update(requestDTO);

        List<BoardImage> existingImages = boardImageRepository.findAllByBoardId(board.getId());
        boardImageRepository.deleteAll(existingImages);

        if (requestDTO.getImageUrls() != null && !requestDTO.getImageUrls().isEmpty()) {
            saveBoardImages(requestDTO.getImageUrls(), board);
        }

        return board.getId();
    }

    public void delete(Long id) {
        Board board = boardReader.findById(id);
        boardImageRepository.deleteByBoard(board);
        boardRepository.delete(board);
    }

    private void saveBoardImages(List<String> imageUrls, Board board) {
        if (imageUrls == null || imageUrls.isEmpty()) return;

        List<BoardImage> boardImages = imageUrls.stream()
                .map(url -> BoardImage.builder()
                        .url(url)
                        .board(board)
                        .build())
                .collect(Collectors.toList());

        boardImageRepository.saveAll(boardImages);
    }
}