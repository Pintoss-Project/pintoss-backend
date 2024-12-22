package pintoss.giftmall.domains.board.infra;

import pintoss.giftmall.common.enums.BoardType;
import pintoss.giftmall.domains.board.dto.BoardResponse;

import java.util.List;

public interface CustomBoardRepository {
    //타입별 게시글 조회
    List<BoardResponse> findAllByType(BoardType type);
}
