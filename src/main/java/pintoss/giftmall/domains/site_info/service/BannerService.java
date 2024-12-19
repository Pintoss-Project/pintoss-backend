package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.enums.BannerImageCategory;
import pintoss.giftmall.domains.site_info.domain.Banner;
import pintoss.giftmall.domains.site_info.domain.BannerImage;
import pintoss.giftmall.domains.site_info.dto.BannerRequest;
import pintoss.giftmall.domains.site_info.dto.BannerResponse;
import pintoss.giftmall.domains.site_info.infra.BannerImageRepository;
import pintoss.giftmall.domains.site_info.infra.BannerReader;
import pintoss.giftmall.domains.site_info.infra.BannerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BannerService {

    private final BannerRepository bannerRepository;
    private final BannerReader bannerReader;
    private final BannerImageRepository bannerImageRepository;

    @Transactional(readOnly = true)
    public List<BannerResponse> findAll() {
        return bannerRepository.findAll().stream()
                .map(banner -> {
                    String desktopImageUrl = bannerImageRepository.findByBannerAndCategory(banner, BannerImageCategory.DESKTOP)
                            .map(BannerImage::getUrl)
                            .orElse(null);
                    String mobileImageUrl = bannerImageRepository.findByBannerAndCategory(banner, BannerImageCategory.MOBILE)
                            .map(BannerImage::getUrl)
                            .orElse(null);
                    return BannerResponse.fromEntity(banner, desktopImageUrl, mobileImageUrl);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BannerResponse findById(Long id) {
        Banner banner = bannerReader.findById(id);
        String desktopImageUrl = bannerImageRepository.findByBannerAndCategory(banner, BannerImageCategory.DESKTOP)
                .map(BannerImage::getUrl)
                .orElse(null);
        String mobileImageUrl = bannerImageRepository.findByBannerAndCategory(banner, BannerImageCategory.MOBILE)
                .map(BannerImage::getUrl)
                .orElse(null);
        return BannerResponse.fromEntity(banner, desktopImageUrl, mobileImageUrl);
    }

    public BannerResponse create(BannerRequest requestDTO) {
        Banner banner = requestDTO.toEntity();
        bannerRepository.save(banner);

        BannerImage desktopImage = BannerImage.builder()
                .url(requestDTO.getDesktopImageUrl())
                .banner(banner)
                .category(BannerImageCategory.DESKTOP)
                .build();
        BannerImage mobileImage = BannerImage.builder()
                .url(requestDTO.getMobileImageUrl())
                .banner(banner)
                .category(BannerImageCategory.MOBILE)
                .build();

        bannerImageRepository.save(desktopImage);
        bannerImageRepository.save(mobileImage);

        return BannerResponse.fromEntity(banner, desktopImage.getUrl(), mobileImage.getUrl());
    }

    public BannerResponse update(Long id, BannerRequest requestDTO) {
        Banner banner = bannerReader.findById(id);
        banner.update(requestDTO.getBannerTitle(), requestDTO.getBannerLink());

        bannerImageRepository.findByBannerAndCategory(banner, BannerImageCategory.DESKTOP)
                .ifPresentOrElse(desktopImage -> {
                    desktopImage.updateUrl(requestDTO.getDesktopImageUrl());
                }, () -> {
                    BannerImage newDesktopImage = BannerImage.builder()
                            .url(requestDTO.getDesktopImageUrl())
                            .banner(banner)
                            .category(BannerImageCategory.DESKTOP)
                            .build();
                    bannerImageRepository.save(newDesktopImage);
                });

        bannerImageRepository.findByBannerAndCategory(banner, BannerImageCategory.MOBILE)
                .ifPresentOrElse(mobileImage -> {
                    mobileImage.updateUrl(requestDTO.getMobileImageUrl());
                }, () -> {
                    BannerImage newMobileImage = BannerImage.builder()
                            .url(requestDTO.getMobileImageUrl())
                            .banner(banner)
                            .category(BannerImageCategory.MOBILE)
                            .build();
                    bannerImageRepository.save(newMobileImage);
                });

        return BannerResponse.fromEntity(banner, requestDTO.getDesktopImageUrl(), requestDTO.getMobileImageUrl());
    }


    public void delete(Long id) {
        Banner banner = bannerReader.findById(id);
        bannerImageRepository.deleteByBanner(banner);
        bannerRepository.deleteById(id);
    }

}