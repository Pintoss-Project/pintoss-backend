package pintoss.giftmall.domains.product.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.product.domain.ProductImage;
import pintoss.giftmall.domains.product.service.port.ProductImageRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductImageRepositoryImpl implements ProductImageRepository {

    private final ProductImageJpaRepository productImageJpaRepository;

    @Override
    public void save(ProductImage productImage) {
        productImageJpaRepository.save(productImage);
    }

    @Override
    public List<ProductImage> findAllByProductId(Long productId) {
        return productImageJpaRepository.findAllByProductId(productId);
    }

    @Override
    public void delete(ProductImage productImage) {
        productImageJpaRepository.delete(productImage);
    }

    @Override
    public void saveAll(List<ProductImage> productImages) {
        productImageJpaRepository.saveAll(productImages);
    }

}
