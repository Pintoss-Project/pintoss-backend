package pintoss.giftmall.domains.board.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.enums.BoardType;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.board.dto.BoardResponse;
import pintoss.giftmall.domains.board.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Validated
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ApiResponse<List<BoardResponse>> getAllBoardByType(@RequestParam(name = "type") @NotNull BoardType type) {
        List<BoardResponse> boards = boardService.findAllByType(type);
        return ApiResponse.ok(boards);
    }

    @GetMapping("/{id}")
    public ApiResponse<BoardResponse> getBoardById(@PathVariable(name="id") Long id) {
        BoardResponse board = boardService.findById(id);
        return ApiResponse.ok(board);
    }

}
