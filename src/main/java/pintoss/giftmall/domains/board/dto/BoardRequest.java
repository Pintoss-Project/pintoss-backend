package pintoss.giftmall.domains.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.board.domain.Board;

@Getter
@NoArgsConstructor
public class BoardRequest {

    @NotBlank
    private String type;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String writer;

    @Builder
    public BoardRequest(String type, String title, String content, String writer) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Board toEntity() {
        return Board.builder()
                .type(this.type)
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .build();
    }

}
