package pintoss.giftmall.domains.site_info.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponse;
import pintoss.giftmall.domains.site_info.service.SiteInfoService;

import java.util.List;

@RestController
@RequestMapping("/api/site-info")
@RequiredArgsConstructor
public class SiteInfoController {

    private final SiteInfoService siteInfoService;

    @GetMapping
    public ApiResponse<List<SiteInfoResponse>> getAllSiteInfo() {
        List<SiteInfoResponse> siteInfoList = siteInfoService.findAll();
        return ApiResponse.ok(siteInfoList);
    }

    @GetMapping("/{id}")
    public ApiResponse<SiteInfoResponse> getSiteInfoById(@PathVariable Long id) {
        SiteInfoResponse siteInfo = siteInfoService.findById(id);
        return ApiResponse.ok(siteInfo);
    }

}
