package pintoss.giftmall.domains.site_info.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponseDTO;
import pintoss.giftmall.domains.site_info.service.SiteInfoService;

import java.util.List;

@RestController
@RequestMapping("/api/site-info")
@RequiredArgsConstructor
public class SiteInfoController {

    private final SiteInfoService siteInfoService;

    @GetMapping
    public ApiResponse<List<SiteInfoResponseDTO>> getAllSiteInfo() {
        List<SiteInfoResponseDTO> siteInfoList = siteInfoService.findAll();
        return ApiResponse.ok(siteInfoList);
    }

    @GetMapping("/{id}")
    public ApiResponse<SiteInfoResponseDTO> getSiteInfoById(@PathVariable Long id) {
        SiteInfoResponseDTO siteInfo = siteInfoService.findById(id);
        return ApiResponse.ok(siteInfo);
    }

}
