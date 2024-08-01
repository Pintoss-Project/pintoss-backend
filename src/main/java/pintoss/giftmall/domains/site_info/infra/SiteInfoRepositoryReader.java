package pintoss.giftmall.domains.site_info.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;

@Component
@RequiredArgsConstructor
public class SiteInfoRepositoryReader {

    private final SiteInfoRepository siteInfoRepository;

    public SiteInfo findById(Long id) {
        return siteInfoRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "사이트 정보 id를 다시 확인해주세요."));
    }

}
