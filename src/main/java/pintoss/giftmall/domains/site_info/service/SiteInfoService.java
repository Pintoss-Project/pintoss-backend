package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponseDTO;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequestDTO;
import pintoss.giftmall.domains.site_info.infra.SiteInfoImageRepository;
import pintoss.giftmall.domains.site_info.infra.SiteInfoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteInfoService {

    private final SiteInfoRepository siteInfoRepository;
    private final SiteInfoImageRepository siteInfoImageRepository;

    public List<SiteInfoResponseDTO> findAll() {
        return siteInfoRepository.findAll().stream()
                .map(SiteInfoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public SiteInfoResponseDTO findById(Long id) {
        SiteInfo siteInfo = siteInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("site_id: " + id));
        return new SiteInfoResponseDTO(siteInfo);
    }

    @Transactional
    public SiteInfoResponseDTO update(Long id, SiteInfoUpdateRequestDTO requestDTO) {
        SiteInfo siteInfo = siteInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("site_id" + id));
        siteInfo.update(requestDTO.getName(), requestDTO.getTel(), requestDTO.getBusinessHour(), requestDTO.getAddress(),
                requestDTO.getOwner(), requestDTO.getBusinesses(), requestDTO.getReportNumber(), requestDTO.getEmail(), requestDTO.getKakao(), requestDTO.getOpenChat());
        return SiteInfoResponseDTO.fromEntity(siteInfo);
    }

}
