package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.enums.SiteInfoImageCategory;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;
import pintoss.giftmall.domains.site_info.domain.SiteInfoImage;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponse;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequest;
import pintoss.giftmall.domains.site_info.infra.SiteInfoImageRepository;
import pintoss.giftmall.domains.site_info.infra.SiteInfoRepository;
import pintoss.giftmall.domains.site_info.infra.SiteInfoReader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SiteInfoService {

    private final SiteInfoRepository siteInfoRepository;
    private final SiteInfoReader siteInfoReader;
    private final SiteInfoImageRepository siteInfoImageRepository;

    @Transactional(readOnly = true)
    public List<SiteInfoResponse> findAll() {
        List<SiteInfo> siteInfos = siteInfoRepository.findAll();

        if (siteInfos.isEmpty()) {
            throw new NotFoundException("사이트 정보를 찾을 수 없습니다.");
        }

        return siteInfos.stream()
                .map(siteInfo -> {
                    Optional<SiteInfoImage> topImage = siteInfoImageRepository.findBySiteInfoIdAndCategory(siteInfo.getId(), SiteInfoImageCategory.TOP);
                    Optional<SiteInfoImage> bottomImage = siteInfoImageRepository.findBySiteInfoIdAndCategory(siteInfo.getId(), SiteInfoImageCategory.BOTTOM);

                    return SiteInfoResponse.fromEntity(siteInfo,
                            topImage.map(SiteInfoImage::getUrl).orElse(null),
                            bottomImage.map(SiteInfoImage::getUrl).orElse(null));
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SiteInfoResponse findById(Long id) {
        SiteInfo siteInfo = siteInfoReader.findById(id);

        Optional<SiteInfoImage> topImage = siteInfoImageRepository.findBySiteInfoIdAndCategory(siteInfo.getId(), SiteInfoImageCategory.TOP);
        Optional<SiteInfoImage> bottomImage = siteInfoImageRepository.findBySiteInfoIdAndCategory(siteInfo.getId(), SiteInfoImageCategory.BOTTOM);

        return SiteInfoResponse.fromEntity(siteInfo,
                topImage.map(SiteInfoImage::getUrl).orElse(null),
                bottomImage.map(SiteInfoImage::getUrl).orElse(null));
    }

    public SiteInfoResponse update(Long id, SiteInfoUpdateRequest requestDTO) {
        SiteInfo siteInfo = siteInfoRepository.findById(id).orElseGet(() -> {
            SiteInfo newSiteInfo = SiteInfo.builder()
                    .name(requestDTO.getName())
                    .tel(requestDTO.getTel())
                    .businessHour(requestDTO.getBusinessHour())
                    .address(requestDTO.getAddress())
                    .owner(requestDTO.getOwner())
                    .businesses(requestDTO.getBusinesses())
                    .reportNumber(requestDTO.getReportNumber())
                    .email(requestDTO.getEmail())
                    .kakao(requestDTO.getKakao())
                    .openChat(requestDTO.getOpenChat())
                    .build();
            return siteInfoRepository.save(newSiteInfo);
        });

        siteInfo.update(requestDTO);

        Optional<SiteInfoImage> existingTopImage = siteInfoImageRepository.findBySiteInfoIdAndCategory(siteInfo.getId(), SiteInfoImageCategory.TOP);
        Optional<SiteInfoImage> existingBottomImage = siteInfoImageRepository.findBySiteInfoIdAndCategory(siteInfo.getId(), SiteInfoImageCategory.BOTTOM);

        if (requestDTO.getTopImageUrl() != null &&
                (!existingTopImage.isPresent() || !existingTopImage.get().getUrl().equals(requestDTO.getTopImageUrl()))) {

            existingTopImage.ifPresent(siteInfoImageRepository::delete);
            saveSiteInfoImage(requestDTO.getTopImageUrl(), siteInfo, SiteInfoImageCategory.TOP);
        }

        if (requestDTO.getBottomImageUrl() != null &&
                (!existingBottomImage.isPresent() || !existingBottomImage.get().getUrl().equals(requestDTO.getBottomImageUrl()))) {

            existingBottomImage.ifPresent(siteInfoImageRepository::delete);
            saveSiteInfoImage(requestDTO.getBottomImageUrl(), siteInfo, SiteInfoImageCategory.BOTTOM);
        }

        Optional<SiteInfoImage> topImage = siteInfoImageRepository.findBySiteInfoIdAndCategory(siteInfo.getId(), SiteInfoImageCategory.TOP);
        Optional<SiteInfoImage> bottomImage = siteInfoImageRepository.findBySiteInfoIdAndCategory(siteInfo.getId(), SiteInfoImageCategory.BOTTOM);

        return SiteInfoResponse.fromEntity(siteInfo,
                topImage.map(SiteInfoImage::getUrl).orElse(null),
                bottomImage.map(SiteInfoImage::getUrl).orElse(null));
    }

    private void saveSiteInfoImage(String imageUrl, SiteInfo siteInfo, SiteInfoImageCategory category) {
        SiteInfoImage siteInfoImage = SiteInfoImage.builder()
                .url(imageUrl)
                .siteInfo(siteInfo)
                .category(category)
                .build();
        siteInfoImageRepository.save(siteInfoImage);
    }
}
