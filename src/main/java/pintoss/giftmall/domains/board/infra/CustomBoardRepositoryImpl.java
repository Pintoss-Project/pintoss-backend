package pintoss.giftmall.domains.board.infra;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.util.ObjectUtils;
import pintoss.giftmall.common.enums.BoardType;
import pintoss.giftmall.common.exceptions.client.FieldMissingException;
import pintoss.giftmall.domains.board.domain.Board;
import pintoss.giftmall.domains.board.domain.QBoard;
import pintoss.giftmall.domains.board.domain.QBoardImage;
import pintoss.giftmall.domains.board.dto.BoardResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory queryFactory;

    public CustomBoardRepositoryImpl(EntityManager entityManager){
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<BoardResponse> findAllByType(BoardType type) {
        if (ObjectUtils.isEmpty(type)) {
            throw new FieldMissingException("type");
        }

        QBoard board = QBoard.board;
        QBoardImage boardImage = QBoardImage.boardImage;

        // QueryDSL을 사용하여 Board와 관련된 BoardImage 데이터를 조인하여 조회
        List<Tuple> results = queryFactory
                .select(board, boardImage.url)
                .from(board)
                .join(boardImage).on(board.id.eq(boardImage.board.id))
                .where(board.type.eq(type))
                .fetch();

        // 결과를 Java 컬렉션에 매핑
        Map<Long, List<String>> boardImageMap = results.stream()
                .filter(tuple -> tuple.get(board.id) != null) // null 체크 추가
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(board.id),
                        Collectors.mapping(tuple -> tuple.get(boardImage.url), Collectors.toList())
                ));


        // Board 데이터를 BoardResponse로 변환, 이미지 URL을 맵에서 가져옴
        return results.stream()
                .map(tuple -> {
                    Board boardEntity = tuple.get(board);
                    List<String> imageUrls = boardImageMap.getOrDefault(boardEntity.getId(), Collections.emptyList());
                    return BoardResponse.fromEntity(boardEntity, imageUrls);
                })
                .distinct()
                .collect(Collectors.toList());
    }
}
