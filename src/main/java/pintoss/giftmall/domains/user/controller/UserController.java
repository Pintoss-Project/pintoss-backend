package pintoss.giftmall.domains.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.user.dto.UserResponse;
import pintoss.giftmall.domains.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/user_info")
    public ApiResponse<UserResponse> getUser(@CookieValue("accessToken") @NotBlank String token) {
        String cleanedToken = token.replace("bearer ", "");
        UserResponse user = userService.getUserInfo(cleanedToken);
        return ApiResponse.ok(user);
    }

}
