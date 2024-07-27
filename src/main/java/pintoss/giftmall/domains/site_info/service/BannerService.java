package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.site_info.domain.Banner;
import pintoss.giftmall.domains.site_info.dto.BannerRequest;
import pintoss.giftmall.domains.site_info.dto.BannerResponse;
import pintoss.giftmall.domains.site_info.infra.BannerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BannerService {

    private final BannerRepository bannerRepository;

    @Transactional(readOnly = true)
    public List<BannerResponse> findAll() {
        return bannerRepository.findAll().stream()
                .map(BannerResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BannerResponse findById(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("banner_id: " + id));

        return BannerResponse.fromEntity(banner);
    }

    public BannerResponse create(BannerRequest requestDTO) {
        Banner banner = requestDTO.toEntity();
        bannerRepository.save(banner);

        return BannerResponse.fromEntity(banner);
    }

    public BannerResponse update(Long id, BannerRequest requestDTO) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("banner_id: " + id));
        banner.update(requestDTO.getBannerTitle(), requestDTO.getBannerLink());

        return BannerResponse.fromEntity(banner);
    }

    public void delete(Long id) {
        bannerRepository.deleteById(id);
    }

}
