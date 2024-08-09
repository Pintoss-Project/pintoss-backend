package pintoss.giftmall.domains.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.user.dto.UserResponse;
import pintoss.giftmall.domains.user.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
public class UserAdminController {

    private final UserService userService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/list")
    public ApiResponse<Page<UserResponse>> getUsers(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        LocalDate start = StringUtils.hasText(startDate) ? LocalDate.parse(startDate, formatter) : null;
        LocalDate end = StringUtils.hasText(endDate) ? LocalDate.parse(endDate, formatter) : null;

        Page<UserResponse> response = userService.findUsersByDateAndKeywordPaged(start, end, search, page, size);
        return ApiResponse.ok(response);
    }

}
