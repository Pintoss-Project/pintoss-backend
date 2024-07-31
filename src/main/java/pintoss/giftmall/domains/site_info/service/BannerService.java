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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BannerService {

    private final BannerRepository bannerRepository;

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
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "배너 id를 다시 확인해주세요."));
        return BannerResponse.fromEntity(banner);
    }

    public BannerResponse create(BannerRequest requestDTO) {
        try {
            Banner banner = requestDTO.toEntity();
            bannerRepository.save(banner);
            return BannerResponse.fromEntity(banner);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "배너", ErrorCode.CREATION_FAILURE);
        }
    }

    public BannerResponse update(Long id, BannerRequest requestDTO) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "배너 id를 다시 확인해주세요."));

        try {
            banner.update(requestDTO.getBannerTitle(), requestDTO.getBannerLink());
            return BannerResponse.fromEntity(banner);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "배너", ErrorCode.UPDATE_FAILURE);
        }
    }

    public void delete(Long id) {
        try {
            bannerRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "배너", ErrorCode.DELETION_FAILURE);
        }
    }
}
