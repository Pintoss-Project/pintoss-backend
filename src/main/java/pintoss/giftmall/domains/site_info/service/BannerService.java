package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.site_info.domain.Banner;
import pintoss.giftmall.domains.site_info.dto.BannerRequest;
import pintoss.giftmall.domains.site_info.dto.BannerResponse;
import pintoss.giftmall.domains.site_info.infra.BannerRepository;
import pintoss.giftmall.domains.site_info.infra.BannerRepositoryReader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BannerService {

    private final BannerRepository bannerRepository;
    private final BannerRepositoryReader bannerRepositoryReader;

    @Transactional(readOnly = true)
    public List<BannerResponse> findAll() {
        List<Banner> banners = bannerRepository.findAll();

        if (banners.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "배너를 찾을 수 없습니다.");
        }

        return banners.stream()
                .map(BannerResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BannerResponse findById(Long id) {
        Banner banner = bannerRepositoryReader.findById(id);

        return BannerResponse.fromEntity(banner);
    }

    public BannerResponse create(BannerRequest requestDTO) {
        Banner banner = requestDTO.toEntity();
        bannerRepository.save(banner);

        return BannerResponse.fromEntity(banner);
    }

    public BannerResponse update(Long id, BannerRequest requestDTO) {
        Banner banner = bannerRepositoryReader.findById(id);
        banner.update(requestDTO.getBannerTitle(), requestDTO.getBannerLink());

        return BannerResponse.fromEntity(banner);
    }

    public void delete(Long id) {
        bannerRepository.deleteById(id);
    }
}
