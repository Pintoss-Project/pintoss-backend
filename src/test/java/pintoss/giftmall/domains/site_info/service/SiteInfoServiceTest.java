package pintoss.giftmall.domains.site_info.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintoss.giftmall.domains.site_info.dto.SiteInfoResponse;
import pintoss.giftmall.domains.site_info.dto.SiteInfoUpdateRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SiteInfoServiceTest {

    @Autowired
    private SiteInfoService siteInfoService;

    @Test
    void testFindAll() {

        List<SiteInfoResponse> siteInfos = siteInfoService.findAll();

        assertThat(siteInfos).hasSize(1);

    }

    @Test
    void testFindById() {

        Long siteId = 1L;
        String expectedBusinesses = "590-95-01527";

        SiteInfoResponse foundSiteInfo = siteInfoService.findById(siteId);

        assertThat(foundSiteInfo).isNotNull();
        assertThat(foundSiteInfo.getBusinesses()).isEqualTo(expectedBusinesses);

    }

    @Test
    void testUpdate() {

        Long siteId = 1L;
        SiteInfoUpdateRequest updateRequest = SiteInfoUpdateRequest.builder()
                .tel("1544-4202")
                .build();

        SiteInfoResponse updatedSiteInfo = siteInfoService.update(siteId, updateRequest);

        assertThat(updatedSiteInfo).isNotNull();
        assertThat(updatedSiteInfo.getTel()).isEqualTo("1544-4202");

    }

}