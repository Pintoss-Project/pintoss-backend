package pintoss.giftmall.domains.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.board.dto.BoardRequest;
import pintoss.giftmall.domains.board.service.BoardService;

@RestController
@RequestMapping("/api/admin/board")
@RequiredArgsConstructor
@Validated
public class BoardAdminController {

    private final BoardService boardService;

    @PostMapping
    public ApiResponse<Long> createBoard(@Valid @RequestBody BoardRequest requestDTO) {
        Long boardId = boardService.create(requestDTO);
        return ApiResponse.ok(boardId);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> updateBoard(@Valid @PathVariable Long id, BoardRequest requestDTO) {
        Long boardId = boardService.update(id, requestDTO);
        return ApiResponse.ok(boardId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ApiResponse.ok("게시글이 삭제되었습니다.");
    }

}
