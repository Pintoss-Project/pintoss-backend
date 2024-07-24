package pintoss.giftmall.domains.site_info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.site_info.dto.BannerRequestDTO;
import pintoss.giftmall.domains.site_info.dto.BannerResponseDTO;
import pintoss.giftmall.domains.site_info.service.BannerService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/site-banner")
@RequiredArgsConstructor
public class BannerAdminController {

    private final BannerService bannerService;

    @PostMapping
    public ApiResponse<BannerResponseDTO> createBanner(@RequestBody BannerRequestDTO requestDTO) {
        BannerResponseDTO createdBanner = bannerService.create(requestDTO);
        return ApiResponse.ok(createdBanner);
    }

    @PatchMapping("/{id}")
    public ApiResponse<BannerResponseDTO> updateBanner(@PathVariable Long id, @RequestBody BannerRequestDTO requestDTO) {
        BannerResponseDTO updateBanner = bannerService.update(id, requestDTO);
        return ApiResponse.ok(updateBanner);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBanner(@PathVariable Long id) {
        bannerService.delete(id);
        return ApiResponse.ok("배너 삭제 완료");
    }

}
