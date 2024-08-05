package pintoss.giftmall.domains.board.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.common.enums.BoardType;
import pintoss.giftmall.domains.board.domain.Board;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardRequest {

    @NotBlank(message = "게시판 유형은 필수 항목입니다.")
    @Enumerated(EnumType.STRING)
    private BoardType type;

    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;

    @NotBlank(message = "작성자는 필수 항목입니다.")
    private String writer;

    private List<Long> imageIds;

    @Builder
    public BoardRequest(BoardType type, String title, String content, String writer, List<Long> imageIds) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.imageIds = imageIds;
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
