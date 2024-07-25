package pintoss.giftmall.domains.product.service;

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
    void findAllByProductId() {
        List<PriceCategoryResponse> priceCategories = priceCategoryService.findAllByProductId(4L);
        assertThat(priceCategories).hasSize(3);
    }

    @Test
    void findByIdAndProductId() {
        PriceCategoryResponse priceCategory = priceCategoryService.findByIdAndProductId(2L, 4L);
        assertThat(priceCategory).isNotNull();
    }

    @Test
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
    void delete() {
        priceCategoryService.delete(4L, 5L);
        assertThrows(IllegalArgumentException.class, () -> priceCategoryService.findByIdAndProductId(5L, 4L));
    }

    @Test
    void updateStock() {
        priceCategoryService.updateStock(4L, 2L, 200);
        PriceCategoryResponse updatedCategory = priceCategoryService.findByIdAndProductId(2L, 4L);

        assertThat(updatedCategory.getStock()).isEqualTo(200);
    }
}