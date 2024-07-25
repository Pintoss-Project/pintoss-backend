package pintoss.giftmall.domains.site_info.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintoss.giftmall.domains.site_info.dto.BannerRequest;
import pintoss.giftmall.domains.site_info.dto.BannerResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BannerServiceTest {

    @Autowired
    private BannerService bannerService;

    @Test
    void testFindAll() {
        List<BannerResponse> banners = bannerService.findAll();
        assertThat(banners).isNotEmpty();
    }

    @Test
    void testFindById() {
        Long bannerId = 1L;
        BannerResponse foundBanner = bannerService.findById(bannerId);

        assertThat(foundBanner).isNotNull();
        assertThat(foundBanner.getBannerTitle()).isEqualTo("Test Title");
        assertThat(foundBanner.getBannerLink()).isEqualTo("http://testlink.com");
    }

    @Test
    void testCreate() {
        BannerRequest createRequest = BannerRequest.builder()
                .bannerTitle("Test Title")
                .bannerLink("http://testlink.com")
                .build();

        BannerResponse createdBanner = bannerService.create(createRequest);

        assertThat(createdBanner).isNotNull();
        assertThat(createdBanner.getBannerTitle()).isEqualTo("Test Title");
        assertThat(createdBanner.getBannerLink()).isEqualTo("http://testlink.com");
    }

    @Test
    void testUpdate() {
        Long bannerId = 1L;

        BannerRequest updateRequest = BannerRequest.builder()
                .bannerTitle("Updated Title")
                .bannerLink("http://updatedlink.com")
                .build();

        BannerResponse updatedBanner = bannerService.update(bannerId, updateRequest);

        assertThat(updatedBanner).isNotNull();
        assertThat(updatedBanner.getBannerTitle()).isEqualTo("Updated Title");
        assertThat(updatedBanner.getBannerLink()).isEqualTo("http://updatedlink.com");
    }

    @Test
    void testDelete() {
        Long bannerId = 2L;
        bannerService.delete(bannerId);

        assertThatThrownBy(() -> bannerService.findById(bannerId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("banner_id");
    }

}