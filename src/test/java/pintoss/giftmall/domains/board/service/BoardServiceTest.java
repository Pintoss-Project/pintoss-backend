package pintoss.giftmall.domains.board.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintoss.giftmall.common.enums.BoardType;
import pintoss.giftmall.domains.board.dto.BoardRequest;
import pintoss.giftmall.domains.board.dto.BoardResponse;
import pintoss.giftmall.domains.board.infra.BoardRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    private Long existingBoardId;

    @BeforeEach
    void setUp() {
        BoardRequest request = BoardRequest.builder()
                .type(BoardType.NOTICE)
                .title("공지사항1")
                .content("공지내용")
                .writer("관리자")
                .build();
        existingBoardId = boardService.create(request);
    }

    @AfterEach
    void deleteRepository() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("게시판 상세 조회 성공 테스트")
    void findById() {
        BoardResponse board = boardService.findById(existingBoardId);
        assertThat(board).isNotNull();
        assertThat(board.getTitle()).isEqualTo("공지사항1");
    }

    @Test
    @DisplayName("게시글 생성 성공 테스트")
    void create() {
        BoardRequest request = BoardRequest.builder()
                .type(BoardType.FAQS)
                .title("자주묻는질문1")
                .content("자주묻는질문 내용")
                .writer("관리자")
                .build();

        Long boardId = boardService.create(request);
        BoardResponse createdBoard = boardService.findById(boardId);

        assertThat(createdBoard).isNotNull();
        assertThat(createdBoard.getTitle()).isEqualTo("자주묻는질문1");
    }

    @Test
    @DisplayName("게시글 업데이트 성공 테스트")
    void update() {
        BoardRequest updateRequest = BoardRequest.builder()
                .content("공지사항 내용 추가")
                .build();

        boardService.update(existingBoardId, updateRequest);
        BoardResponse updatedBoard = boardService.findById(existingBoardId);

        assertThat(updatedBoard.getContent()).isEqualTo("공지사항 내용 추가");
    }

    @Test
    @DisplayName("게시글 삭제 성공 테스트")
    void delete() {
        boardService.delete(existingBoardId);
        assertThrows(IllegalArgumentException.class, () -> boardService.findById(existingBoardId));
    }

}