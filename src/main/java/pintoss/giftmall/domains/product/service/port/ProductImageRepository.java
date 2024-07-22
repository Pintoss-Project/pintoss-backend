package pintoss.giftmall.domains.product.service.port;

import pintoss.giftmall.domains.product.domain.ProductImage;

import java.util.List;

public interface ProductImageRepository {

    void save(ProductImage productImage);

    List<ProductImage> findAllByProductId(Long productId);

    void delete(ProductImage productImage);

    void saveAll(List<ProductImage> productImages);

}
