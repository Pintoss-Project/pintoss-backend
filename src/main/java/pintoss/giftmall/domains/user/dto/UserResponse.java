package pintoss.giftmall.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.domains.user.domain.User;

@Getter
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String phone;

    @Builder
    public UserResponse(Long id, String email, String name, String phone) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .build();
    }

}
