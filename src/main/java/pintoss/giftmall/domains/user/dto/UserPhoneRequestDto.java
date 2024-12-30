package pintoss.giftmall.domains.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneRequestDto {

    @NotBlank(message = "휴대폰 전화번호를 입력해주세요.")
    private String phone;
}
