package pintoss.giftmall.domains.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.common.enums.BoardType;
import pintoss.giftmall.domains.board.domain.Board;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponse {

    private Long id;
    private BoardType type;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private List<String> imageUrls;

    @Builder
    public BoardResponse(Long id, BoardType type, String title, String content, String writer, List<String> imageUrls) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = LocalDateTime.now();
        this.imageUrls = imageUrls;
    }

    public static BoardResponse fromEntity(Board board, List<String> imageUrls) {
        return BoardResponse.builder()
                .id(board.getId())
                .type(board.getType())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .imageUrls(imageUrls)
                .build();
    }

}

