package pintoss.giftmall.domains.site_info.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.site_info.dto.BannerRequest;
import pintoss.giftmall.domains.site_info.dto.BannerResponse;
import pintoss.giftmall.domains.site_info.service.BannerService;

@RestController
@RequestMapping("/api/admin/site-banner")
@RequiredArgsConstructor
@Validated
public class BannerAdminController {

    private final BannerService bannerService;

    @PostMapping
    public ApiResponse<BannerResponse> createBanner(@RequestBody @Valid BannerRequest requestDTO) {
        BannerResponse createdBanner = bannerService.create(requestDTO);
        return ApiResponse.ok(createdBanner);
    }

    @PatchMapping("/{id}")
    public ApiResponse<BannerResponse> updateBanner(@PathVariable Long id, @RequestBody @Valid BannerRequest requestDTO) {
        BannerResponse updateBanner = bannerService.update(id, requestDTO);
        return ApiResponse.ok(updateBanner);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBanner(@PathVariable Long id) {
        bannerService.delete(id);
        return ApiResponse.ok("배너 삭제 완료");
    }

}
