package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponse;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequest;
import pintoss.giftmall.domains.site_info.infra.SiteInfoImageRepository;
import pintoss.giftmall.domains.site_info.infra.SiteInfoRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteInfoService {

    private final SiteInfoRepository siteInfoRepository;
    private final SiteInfoImageRepository siteInfoImageRepository;

    @Transactional(readOnly = true)
    public List<SiteInfoResponse> findAll() {
        return siteInfoRepository.findAll().stream()
                .map(SiteInfoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SiteInfoResponse findById(Long id) {
        SiteInfo siteInfo = siteInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("site_id: " + id));
        return SiteInfoResponse.fromEntity(siteInfo);
    }

    @Transactional
    public SiteInfoResponse update(Long id, SiteInfoUpdateRequest requestDTO) {
        SiteInfo siteInfo = siteInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("site_id" + id));

        siteInfo.update(requestDTO);

        return SiteInfoResponse.fromEntity(siteInfo);
    }

}
