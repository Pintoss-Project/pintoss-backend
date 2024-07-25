package pintoss.giftmall.domains.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.dto.PriceCategoryRequest;
import pintoss.giftmall.domains.product.dto.PriceCategoryResponse;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.infra.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceCategoryService {

    private final PriceCategoryRepository priceCategoryRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<PriceCategoryResponse> findAllByProductId(Long productId) {
        List<PriceCategory> priceCategories = priceCategoryRepository.findAllByProductId(productId);
        return priceCategories.stream()
                .map(PriceCategoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PriceCategoryResponse findByIdAndProductId(Long categoryId, Long productId) {
        PriceCategory priceCategory = priceCategoryRepository.findByIdAndProductId(categoryId, productId)
                .orElseThrow(() -> new IllegalArgumentException("상품권 정보 또는 가격 카테고리 정보를 찾을 수 없습니다."));
        return PriceCategoryResponse.fromEntity(priceCategory);
    }


    @Transactional
    public Long create(PriceCategoryRequest requestDTO) {
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("product_id : " + requestDTO.getProductId()));

        PriceCategory priceCategory = requestDTO.toEntity(product);
        priceCategoryRepository.save(priceCategory);

        return priceCategory.getId();
    }

    @Transactional
    public void delete(Long productId, Long categoryId) {
        PriceCategory priceCategory = priceCategoryRepository.findByIdAndProductId(categoryId, productId)
                .orElseThrow(() -> new IllegalArgumentException("상품권 정보 또는 가격 카테고리 정보를 찾을 수 없습니다."));
        priceCategoryRepository.delete(priceCategory);
    }

    @Transactional
    public Long updateStock(Long productId, Long categoryId, int stock) {
        PriceCategory priceCategory = priceCategoryRepository.findByIdAndProductId(categoryId, productId)
                .orElseThrow(() -> new IllegalArgumentException("상품권 정보 또는 가격 카테고리 정보를 찾을 수 없습니다."));

        priceCategory.updateStock(stock);

        return priceCategory.getId();
    }

}
