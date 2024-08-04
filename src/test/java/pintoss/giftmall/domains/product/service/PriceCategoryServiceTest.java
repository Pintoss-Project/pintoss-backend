package pintoss.giftmall.domains.product.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintoss.giftmall.domains.product.dto.PriceCategoryRequest;
import pintoss.giftmall.domains.product.dto.PriceCategoryResponse;

import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PriceCategoryServiceTest {

    @Autowired
    private PriceCategoryService priceCategoryService;

    @Test
    @DisplayName("상품별 가격 카테고리 리스트 조회 성공 테스트")
    void findAllByProductId() {
        List<PriceCategoryResponse> priceCategories = priceCategoryService.findAllByProductId(4L);
        assertThat(priceCategories).hasSize(3);
    }

    @Test
    @DisplayName("상품별 가격 카테고리 조회 성공 테스트")
    void findByIdAndProductId() {
        PriceCategoryResponse priceCategory = priceCategoryService.findByIdAndProductId(2L, 4L);
        assertThat(priceCategory).isNotNull();
    }

    @Test
    @DisplayName("가격 카테고리 생성 성공 테스트")
    void create() {
        PriceCategoryRequest request = PriceCategoryRequest.builder()
                .name("50000원권")
                .price(50000)
                .stock(500)
                .productId(4L)
                .build();

        Long categoryId = priceCategoryService.create(request);
        PriceCategoryResponse createdCategory = priceCategoryService.findByIdAndProductId(categoryId, 4L);

        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getName()).isEqualTo("50000원권");
    }

    @Test
    @DisplayName("가격 카테고리 삭제 성공 테스트")
    void delete() {
        priceCategoryService.delete(4L, 5L);
        assertThrows(IllegalArgumentException.class, () -> priceCategoryService.findByIdAndProductId(5L, 4L));
    }

    @Test
    @DisplayName("상품 재고 업데이트 성공 테스트")
    void updateStock() {
        priceCategoryService.updateStock(4L, 2L, 200);
        PriceCategoryResponse updatedCategory = priceCategoryService.findByIdAndProductId(2L, 4L);

        assertThat(updatedCategory.getStock()).isEqualTo(200);
    }
}