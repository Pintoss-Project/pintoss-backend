package pintoss.giftmall.domains.site_info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponseDTO;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequestDTO;
import pintoss.giftmall.domains.site_info.service.SiteInfoService;

@RestController
@RequestMapping("/api/admin/site-info")
@RequiredArgsConstructor
public class SiteInfoAdminController {

    private final SiteInfoService siteInfoService;

    @PatchMapping("/{id}")
    public ApiResponse<SiteInfoResponseDTO> updateSiteInfo(@PathVariable Long id, @RequestBody SiteInfoUpdateRequestDTO requestDTO) {
        SiteInfoResponseDTO updateSiteInfo = siteInfoService.update(id, requestDTO);
        return ApiResponse.ok(updateSiteInfo);
    }

}
