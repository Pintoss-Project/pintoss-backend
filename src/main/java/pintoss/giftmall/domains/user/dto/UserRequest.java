package pintoss.giftmall.domains.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private String phone;

    @Builder
    public UserRequest(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

}
