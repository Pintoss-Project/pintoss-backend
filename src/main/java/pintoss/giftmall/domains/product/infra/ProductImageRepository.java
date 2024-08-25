package pintoss.giftmall.domains.product.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.product.domain.ProductImage;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProductId(Long productId);

    Optional<ProductImage> findByProductId(Long productId);

    void deleteByProductId(Long productId);

    default void updateLogoImage(Long productId, String logoImageUrl) {
        findByProductId(productId).ifPresent(productImage -> {
            productImage.updateLogoImage(logoImageUrl);
            save(productImage);
        });
    }

}
