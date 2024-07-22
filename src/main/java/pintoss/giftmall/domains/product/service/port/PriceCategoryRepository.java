package pintoss.giftmall.domains.product.service.port;

import pintoss.giftmall.domains.product.domain.PriceCategory;

import java.util.List;

public interface PriceCategoryRepository {

    List<PriceCategory> findAll();

    PriceCategory register(PriceCategory priceCategory);

    void deleteById(Long id);

    PriceCategory updateStock(Long categoryId, int stock);

}
