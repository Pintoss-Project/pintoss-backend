package pintoss.giftmall.domains.site_info.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SiteImageDTO {

    private String url;
    private String category;

    @Builder
    public SiteImageDTO(String url, String category) {
        this.url = url;
        this.category = category;
    }

}
