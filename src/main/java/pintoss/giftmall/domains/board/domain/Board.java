package pintoss.giftmall.domains.board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;
import pintoss.giftmall.domains.board.dto.BoardRequest;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 20)
    private String type;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 20)
    private String writer;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Board(String type, String title, String content, String writer) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void update(BoardRequest requestDTO) {
        if(StringUtils.hasText(requestDTO.getTitle())) this.title = requestDTO.getTitle();
        if(StringUtils.hasText(requestDTO.getType())) this.type = requestDTO.getType();
        if(StringUtils.hasText(requestDTO.getContent())) this.content = requestDTO.getContent();
        if(StringUtils.hasText(requestDTO.getWriter())) this.writer = requestDTO.getWriter();
    }

}
