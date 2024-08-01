package pintoss.giftmall.domains.board.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.board.domain.Board;

@Component
@RequiredArgsConstructor
public class BoardReader {

    private final BoardRepository boardRepository;

    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new NotFoundException("게시판 id를 다시 확인해주세요."));
    }

}
