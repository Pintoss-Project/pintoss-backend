package pintoss.giftmall.domains.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String grantType;
    private String accessToken;
    private String refreshToken;

}
