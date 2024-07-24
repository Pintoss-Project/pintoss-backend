package pintoss.giftmall.domains.site_info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.domains.site_info.dto.BannerResponseDTO;
import pintoss.giftmall.domains.site_info.service.BannerService;

import java.util.List;

@RestController
@RequestMapping("/api/site-banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<List<BannerResponseDTO>> getAllBanner() {
        List<BannerResponseDTO> bannerList = bannerService.findAll();
        return ResponseEntity.ok(bannerList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BannerResponseDTO> getBannerById(@PathVariable Long id) {
        BannerResponseDTO banner = bannerService.findById(id);
        return ResponseEntity.ok(banner);
    }

}
