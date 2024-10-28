package pintoss.giftmall.domains.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestToken {

    private String accessToken;
    private String refreshToken;
}
