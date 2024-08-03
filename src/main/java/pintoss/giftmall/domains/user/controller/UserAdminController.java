package pintoss.giftmall.domains.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.user.dto.UserResponse;
import pintoss.giftmall.domains.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
public class UserAdminController {

    private final UserService userService;

    @GetMapping("/all")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ApiResponse.ok(response);
    }

    @GetMapping("/filter")
    public ApiResponse<List<UserResponse>> getFilteredUsers(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate, @RequestParam String search) {
        List<UserResponse> response = userService.findUsersByDateAndKeyword(startDate, endDate, search);
        return ApiResponse.ok(response);
    }

}
