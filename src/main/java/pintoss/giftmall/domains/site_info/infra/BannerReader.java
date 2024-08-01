package pintoss.giftmall.domains.site_info.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.site_info.domain.Banner;

@Component
@RequiredArgsConstructor
public class BannerReader {

    private final BannerRepository bannerRepository;

    public Banner findById(Long id) {
        return bannerRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "배너 id를 다시 확인해주세요."));
    }

}
