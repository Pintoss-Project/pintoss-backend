package pintoss.giftmall.domains.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.user.dto.UserResponse;
import pintoss.giftmall.domains.user.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
public class UserAdminController {

    private final UserService userService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/all")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return ApiResponse.ok(response);
    }

    @GetMapping("/filter")
    public ApiResponse<List<UserResponse>> getFilteredUsers(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String search) {

        LocalDate start = StringUtils.hasText(startDate) ? LocalDate.parse(startDate, formatter) : null;
        LocalDate end = StringUtils.hasText(endDate) ? LocalDate.parse(endDate, formatter) : null;

        List<UserResponse> response = userService.findUsersByDateAndKeyword(start, end, search);
        return ApiResponse.ok(response);
    }

}
