package pintoss.giftmall.domains.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.common.enums.UserRole;
import pintoss.giftmall.domains.user.domain.User;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private String phone;

    @Builder
    public RegisterRequest(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .phone(this.phone)
                .role(UserRole.USER)
                .build();
    }

}
