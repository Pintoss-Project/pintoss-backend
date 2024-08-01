package pintoss.giftmall.domains.site_info.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.site_info.domain.Banner;

@Component
@RequiredArgsConstructor
public class BannerReader {

    private final BannerRepository bannerRepository;

    public Banner findById(Long id) {
        return bannerRepository.findById(id).orElseThrow(() -> new NotFoundException("배너 id를 다시 확인해주세요."));
    }

}
