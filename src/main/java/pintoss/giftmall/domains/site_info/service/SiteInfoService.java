package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
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
            throw new NotFoundException("사이트 정보를 찾을 수 없습니다.");
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
        SiteInfo siteInfo = siteInfoRepository.findById(id).orElseGet(() -> {
            SiteInfo newSiteInfo = SiteInfo.builder()
                    .name(requestDTO.getName())
                    .tel(requestDTO.getTel())
                    .businessHour(requestDTO.getBusinessHour())
                    .address(requestDTO.getAddress())
                    .owner(requestDTO.getOwner())
                    .businesses(requestDTO.getBusinesses())
                    .reportNumber(requestDTO.getReportNumber())
                    .email(requestDTO.getEmail())
                    .kakao(requestDTO.getKakao())
                    .openChat(requestDTO.getOpenChat())
                    .build();
            return siteInfoRepository.save(newSiteInfo);
        });

        siteInfo.update(requestDTO);

        return SiteInfoResponse.fromEntity(siteInfo);
    }

}
