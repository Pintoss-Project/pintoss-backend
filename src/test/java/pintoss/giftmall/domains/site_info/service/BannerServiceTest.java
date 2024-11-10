package pintoss.giftmall.domains.site_info.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintoss.giftmall.domains.site_info.dto.BannerRequest;
import pintoss.giftmall.domains.site_info.dto.BannerResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Disabled
@SpringBootTest
class BannerServiceTest {

    @Autowired
    private BannerService bannerService;

    @Test
    @DisplayName("배너 리스트 조회 성공 테스트")
    void testFindAll() {
        List<BannerResponse> banners = bannerService.findAll();
        assertThat(banners).isNotEmpty();
    }

    @Test
    @DisplayName("배너 조회 성공 테스트")
    void testFindById() {
        Long bannerId = 1L;
        BannerResponse foundBanner = bannerService.findById(bannerId);

        assertThat(foundBanner).isNotNull();
        assertThat(foundBanner.getBannerTitle()).isEqualTo("Test Title");
        assertThat(foundBanner.getBannerLink()).isEqualTo("http://testlink.com");
    }

    @Test
    @DisplayName("배너 생성 성공 테스트")
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
    @DisplayName("배너 업데이트 성공 테스트")
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
    @DisplayName("배너 삭제 성공 테스트")
    void testDelete() {
        Long bannerId = 2L;
        bannerService.delete(bannerId);

        assertThatThrownBy(() -> bannerService.findById(bannerId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("banner_id");
    }

}