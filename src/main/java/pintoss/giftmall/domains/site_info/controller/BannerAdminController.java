package pintoss.giftmall.domains.site_info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<BannerResponseDTO> createBanner(@RequestBody BannerRequestDTO requestDTO) {
        BannerResponseDTO createdBanner = bannerService.create(requestDTO);
        return ResponseEntity.ok(createdBanner);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BannerResponseDTO> updateBanner(@PathVariable Long id, @RequestBody BannerRequestDTO requestDTO) {
        BannerResponseDTO updateBanner = bannerService.update(id, requestDTO);
        return ResponseEntity.ok(updateBanner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        bannerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
