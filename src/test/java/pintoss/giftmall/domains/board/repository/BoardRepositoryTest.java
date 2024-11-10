package pintoss.giftmall.domains.board.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pintoss.giftmall.common.enums.BoardType;
import pintoss.giftmall.config.QueryDslConfig;
import pintoss.giftmall.domains.board.domain.Board;
import pintoss.giftmall.domains.board.domain.BoardImage;
import pintoss.giftmall.domains.board.domain.QBoard;
import pintoss.giftmall.domains.board.domain.QBoardImage;
import pintoss.giftmall.domains.board.dto.BoardResponse;
import pintoss.giftmall.domains.board.infra.BoardRepository;

import java.util.List;

@Disabled
@DataJpaTest
@Import(QueryDslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;



    @BeforeEach
    void createTest() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    @DisplayName("타입에 따른 모든 게시물과 이미지 조회 테스트-Query")
    public void testFindAllByType() {

        QBoard board = QBoard.board;
        QBoardImage boardImage = QBoardImage.boardImage;

        List<Tuple> results = queryFactory
                .select(board, boardImage.url)
                .from(board)
                .join(boardImage).on(board.id.eq(boardImage.board.id))
                .where(board.type.eq(BoardType.NOTICE))
                .fetch();

        System.out.println("Results: " + results);

    }

    @Test
    @DisplayName("QueryDsl을 적용한 게시글")
    public void testFindAllByType2() {
        List<BoardResponse>list = boardRepository.findAllByType(BoardType.NOTICE);
        System.out.println(list);
    }
}
