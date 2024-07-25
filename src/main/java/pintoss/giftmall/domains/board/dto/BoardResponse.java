package pintoss.giftmall.domains.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.board.domain.Board;

@Getter
@NoArgsConstructor
public class BoardResponse {

    private Long id;
    private String type;
    private String title;
    private String content;
    private String writer;

    @Builder
    public BoardResponse(Long id, String type, String title, String content, String writer) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public static BoardResponse fromEntity(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .type(board.getType())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .build();
    }

}

