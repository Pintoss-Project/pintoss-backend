package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponse;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequest;
import pintoss.giftmall.domains.site_info.infra.SiteInfoRepository;
import pintoss.giftmall.domains.site_info.infra.SiteInfoReader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SiteInfoService {

    private final SiteInfoRepository siteInfoRepository;
    private final SiteInfoReader siteInfoReader;

    @Transactional(readOnly = true)
    public List<SiteInfoResponse> findAll() {
        List<SiteInfo> siteInfos = siteInfoRepository.findAll();
        if (siteInfos.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "사이트 정보를 찾을 수 없습니다.");
        }
        return siteInfos.stream()
                .map(SiteInfoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SiteInfoResponse findById(Long id) {
        SiteInfo siteInfo = siteInfoReader.findById(id);

        return SiteInfoResponse.fromEntity(siteInfo);
    }

    public SiteInfoResponse update(Long id, SiteInfoUpdateRequest requestDTO) {
        SiteInfo siteInfo = siteInfoReader.findById(id);
        siteInfo.update(requestDTO);

        return SiteInfoResponse.fromEntity(siteInfo);
    }
}
