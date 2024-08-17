package pintoss.giftmall.domains.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NaverCallbackRequest {

    @NotBlank(message = "코드는은 필수 항목입니다.")
    private String code;

    @NotBlank(message = "상태값은 필수 항목입니다.")
    private String state;

}
