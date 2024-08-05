package pintoss.giftmall.domains.image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageResponse {

    private Long id;
    private String url;

    @Builder
    public ImageResponse(Long id, String url) {
        this.id = id;
        this.url = url;
    }

}
