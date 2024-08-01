package pintoss.giftmall.domains.site_info.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;

@Component
@RequiredArgsConstructor
public class SiteInfoReader {

    private final SiteInfoRepository siteInfoRepository;

    public SiteInfo findById(Long id) {
        return siteInfoRepository.findById(id).orElseThrow(() -> new NotFoundException("사이트 정보 id를 다시 확인해주세요."));
    }

}
