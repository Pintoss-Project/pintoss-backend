package pintoss.giftmall.domains.site_info.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.site_info.dto.BannerResponse;
import pintoss.giftmall.domains.site_info.service.BannerService;

import java.util.List;

@RestController
@RequestMapping("/api/site-banner")
@RequiredArgsConstructor
@Validated
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public ApiResponse<List<BannerResponse>> getAllBanner() {
        List<BannerResponse> bannerList = bannerService.findAll();
        return ApiResponse.ok(bannerList);
    }

    @GetMapping("/{id}")
    public ApiResponse<BannerResponse> getBannerById(@PathVariable @NotNull Long id) {
        BannerResponse banner = bannerService.findById(id);
        return ApiResponse.ok(banner);
    }

}
