package pintoss.giftmall.domains.site_info.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponse;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequest;
import pintoss.giftmall.domains.site_info.service.SiteInfoService;

@RestController
@RequestMapping("/api/admin/site-info")
@RequiredArgsConstructor
@Validated
public class SiteInfoAdminController {

    private final SiteInfoService siteInfoService;

    @PatchMapping("/{id}")
    public ApiResponse<SiteInfoResponse> updateSiteInfo(@PathVariable Long id, @RequestBody @Valid SiteInfoUpdateRequest requestDTO) {
        SiteInfoResponse updateSiteInfo = siteInfoService.update(id, requestDTO);
        return ApiResponse.ok(updateSiteInfo);
    }

}
