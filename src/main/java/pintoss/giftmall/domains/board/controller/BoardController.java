package pintoss.giftmall.domains.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.board.dto.BoardResponse;
import pintoss.giftmall.domains.board.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ApiResponse<List<BoardResponse>> getAllBoardByType(@RequestParam String type) {
        List<BoardResponse> boards = boardService.findAllByType(type);
        return ApiResponse.ok(boards);
    }

    @GetMapping("/{id")
    public ApiResponse<BoardResponse> getBoardById(@PathVariable Long id) {
        BoardResponse board = boardService.findById(id);
        return ApiResponse.ok(board);
    }

}
