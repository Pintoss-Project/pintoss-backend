package pintoss.giftmall.domains.site_info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponseDTO;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequestDTO;
import pintoss.giftmall.domains.site_info.service.SiteInfoService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SiteInfoController {

    private final SiteInfoService siteInfoService;

    @GetMapping("/site-info")
    public ResponseEntity<List<SiteInfoResponseDTO>> getAllSiteInfo() {
        List<SiteInfoResponseDTO> siteInfoList = siteInfoService.findAll();
        return ResponseEntity.ok(siteInfoList);
    }

    @GetMapping("/site-info/{id}")
    public ResponseEntity<SiteInfoResponseDTO> getSiteInfoById(@PathVariable Long id) {
        SiteInfoResponseDTO siteInfo = siteInfoService.findById(id);
        return ResponseEntity.ok(siteInfo);
    }

    @PatchMapping("/admin/site-info/{id}")
    public ResponseEntity<SiteInfoResponseDTO> updateSiteInfo(@PathVariable Long id, @RequestBody SiteInfoUpdateRequestDTO requestDTO) {
        SiteInfoResponseDTO updateSiteInfo = siteInfoService.update(id, requestDTO);
        return ResponseEntity.ok(updateSiteInfo);
    }

}
