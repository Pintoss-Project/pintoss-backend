package pintoss.giftmall.domains.board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.image.domain.Image;

@Entity
@Getter
@NoArgsConstructor
public class BoardImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public BoardImage(String url, Board board) {
        super(url);
        this.board = board;
    }
}
