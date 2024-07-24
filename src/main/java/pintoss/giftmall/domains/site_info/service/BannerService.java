package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.site_info.domain.Banner;
import pintoss.giftmall.domains.site_info.dto.BannerRequestDTO;
import pintoss.giftmall.domains.site_info.dto.BannerResponseDTO;
import pintoss.giftmall.domains.site_info.infra.BannerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public List<BannerResponseDTO> findAll() {
        return bannerRepository.findAll().stream()
                .map(BannerResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public BannerResponseDTO findById(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("banner_id: " + id));

        return BannerResponseDTO.fromEntity(banner);
    }

    @Transactional
    public BannerResponseDTO create(BannerRequestDTO requestDTO) {
        Banner banner = requestDTO.toEntity();
        bannerRepository.save(banner);

        return BannerResponseDTO.fromEntity(banner);
    }

    @Transactional
    public BannerResponseDTO update(Long id, BannerRequestDTO requestDTO) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("banner_id: " + id));
        banner.update(requestDTO.getBannerTitle(), requestDTO.getBannerLink());

        return BannerResponseDTO.fromEntity(banner);
    }

    @Transactional
    public void delete(Long id) {
        bannerRepository.deleteById(id);
    }

}
