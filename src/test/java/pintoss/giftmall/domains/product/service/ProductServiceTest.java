package pintoss.giftmall.domains.product.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintoss.giftmall.common.enums.ProductCategory;
import pintoss.giftmall.domains.product.dto.ProductRequest;
import pintoss.giftmall.domains.product.dto.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 리스트 조회 성공 테스트")
    void findAll() {
        List<ProductResponse> products = productService.findAll();
        assertThat(products).isNotNull();
        assertThat(products).hasSize(3);
    }

    @Test
    @DisplayName("상품 상세 조회 성공 테스트")
    void findById() {
        Long productId = 5L;
        ProductResponse product = productService.findById(productId);
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("북앤라이프 도서상품권");
    }

    @Test
    @DisplayName("상품 생성 성공 테스트")
    void create() {
        ProductRequest request = ProductRequest.builder()
                .name("넥슨카드")
                .cardDiscount(new BigDecimal("1.20"))
                .phoneDiscount(new BigDecimal("0.00"))
                .homePage("http://example.com")
                .csCenter("1544-4567")
                .description("상세설명")
                .publisher("넥슨")
                .category(ProductCategory.GO)
                .build();

        Long productId = productService.create(request);
        ProductResponse createdProduct = productService.findById(productId);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo("넥슨카드");
    }

    @Test
    @DisplayName("상품 업데이트 성공 테스트")
    void update() {
        Long productId  = 4L;

        ProductRequest updateRequest = ProductRequest.builder()
                .name("문화상품권")
                .isPopular(true)
                .cardDiscount(new BigDecimal("2.50"))
                .phoneDiscount(new BigDecimal("1.20"))
                .homePage("http://example.com")
                .csCenter("1544-4567")
                .description("상세설명")
                .publisher("문화")
                .category(ProductCategory.CBM)
                .build();

        productService.update(productId, updateRequest);
        ProductResponse updatedProduct = productService.findById(productId);

        assertThat(updatedProduct.getName()).isEqualTo("문화상품권");
        assertThat(updatedProduct.isPopular()).isTrue();
    }

    @Test
    @DisplayName("상품 삭제 성공 테스트")
    void delete() {
        Long productId = 4L;
        productService.delete(productId);
        assertThrows(IllegalArgumentException.class, () -> productService.findById(productId));
    }

    @Test
    @DisplayName("카테고리별 상품 리스트 조회 성공 테스트")
    void findByCategory() {
        List<ProductResponse> products = productService.findByCategory("cbm");
        assertThat(products).hasSize(2);
    }

    @Test
    @DisplayName("인기 상품별 상품 리스트 조회 성공 테스트")
    void findPopularProducts() {
        List<ProductResponse> products = productService.findPopularProducts();
        assertThat(products).hasSize(1);
    }

}