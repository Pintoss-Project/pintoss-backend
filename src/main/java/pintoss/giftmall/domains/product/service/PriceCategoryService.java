package pintoss.giftmall.domains.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.dto.PriceCategoryRequest;
import pintoss.giftmall.domains.product.dto.PriceCategoryResponse;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.infra.PriceCategoryReader;
import pintoss.giftmall.domains.product.infra.ProductReader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PriceCategoryService {

    private final PriceCategoryRepository priceCategoryRepository;
    private final ProductReader productReader;
    private final PriceCategoryReader priceCategoryReader;

    @Transactional(readOnly = true)
    public List<PriceCategoryResponse> findAllByProductId(Long productId) {
        productReader.findById(productId);

        List<PriceCategory> priceCategories = priceCategoryRepository.findAllByProductId(productId);
        if (priceCategories.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "상품의 가격 카테고리를 찾을 수 없습니다.");
        }
        return priceCategories.stream()
                .map(PriceCategoryResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PriceCategoryResponse findByIdAndProductId(Long categoryId, Long productId) {
        PriceCategory priceCategory = priceCategoryReader.findByIdAndProductId(categoryId, productId);

        return PriceCategoryResponse.fromEntity(priceCategory);
    }

    public Long create(PriceCategoryRequest requestDTO) {
        Product product = productReader.findById(requestDTO.getProductId());
        PriceCategory priceCategory = requestDTO.toEntity(product);
        priceCategoryRepository.save(priceCategory);

        return priceCategory.getId();
    }

    public void delete(Long productId, Long categoryId) {
        PriceCategory priceCategory = priceCategoryReader.findByIdAndProductId(categoryId, productId);
        priceCategoryRepository.delete(priceCategory);
    }

    public Long updateStock(Long productId, Long categoryId, int stock) {
        PriceCategory priceCategory = priceCategoryReader.findByIdAndProductId(categoryId, productId);
        priceCategory.updateStock(stock);

        return priceCategory.getId();
    }

}
