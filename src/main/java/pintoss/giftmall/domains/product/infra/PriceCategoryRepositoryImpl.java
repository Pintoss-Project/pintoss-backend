package pintoss.giftmall.domains.product.infra;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.service.port.PriceCategoryRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PriceCategoryRepositoryImpl implements PriceCategoryRepository {

    private final PriceCategoryJpaRepository priceCategoryJpaRepository;
    @Override
    public List<PriceCategory> findAll() {
        return priceCategoryJpaRepository.findAll();
    }

    @Override
    public PriceCategory register(PriceCategory priceCategory) {
        return priceCategoryJpaRepository.save(priceCategory);
    }

    @Override
    public void deleteById(Long id) {
        priceCategoryJpaRepository.deleteById(id);
    }

    @Override
    public PriceCategory updateStock(Long categoryId, int stock) {
        PriceCategory priceCategory = priceCategoryJpaRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);
        priceCategory.setStock(stock);

        return priceCategoryJpaRepository.save(priceCategory);
    }

}
