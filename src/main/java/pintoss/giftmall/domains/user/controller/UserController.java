package pintoss.giftmall.domains.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.user.dto.UserResponse;
import pintoss.giftmall.domains.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/user_info")
    public ApiResponse<UserResponse> getUser(@RequestBody @NotBlank String token) {
        UserResponse user = userService.getUserInfo(token);
        return ApiResponse.ok(user);
    }

}
