package pintoss.giftmall.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.domains.user.domain.User;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private LocalDateTime timestamp;

    @Builder
    public UserResponse(Long id, String email, String name, String phone, LocalDateTime timestamp) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.timestamp = timestamp;
    }

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .timestamp(user.getCreatedAt())
                .build();
    }
}
