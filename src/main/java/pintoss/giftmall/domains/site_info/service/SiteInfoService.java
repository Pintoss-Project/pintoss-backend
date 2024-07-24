package pintoss.giftmall.domains.site_info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.site_info.domain.SiteInfo;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponseDTO;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequestDTO;
import pintoss.giftmall.domains.site_info.infra.SiteInfoImageRepository;
import pintoss.giftmall.domains.site_info.infra.SiteInfoRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteInfoService {

    private final SiteInfoRepository siteInfoRepository;
    private final SiteInfoImageRepository siteInfoImageRepository;

    @Transactional(readOnly = true)
    public List<SiteInfoResponseDTO> findAll() {
        return siteInfoRepository.findAll().stream()
                .map(SiteInfoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SiteInfoResponseDTO findById(Long id) {
        SiteInfo siteInfo = siteInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("site_id: " + id));
        return SiteInfoResponseDTO.fromEntity(siteInfo);
    }

    @Transactional
    public SiteInfoResponseDTO update(Long id, SiteInfoUpdateRequestDTO requestDTO) {
        SiteInfo siteInfo = siteInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("site_id" + id));

        updateNonNullFields(requestDTO, siteInfo);

        return SiteInfoResponseDTO.fromEntity(siteInfo);
    }

    private void updateNonNullFields(Object source, Object target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(source);
                if (value != null) {
                    Field targetField = target.getClass().getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                    targetField.set(target, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
