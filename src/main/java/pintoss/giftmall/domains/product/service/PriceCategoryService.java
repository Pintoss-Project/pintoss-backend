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
import pintoss.giftmall.domains.product.infra.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PriceCategoryService {

    private final PriceCategoryRepository priceCategoryRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<PriceCategoryResponse> findAllByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "상품 id를 다시 확인해주세요."));

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
        PriceCategory priceCategory = priceCategoryRepository.findByIdAndProductId(categoryId, productId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "가격 카테고리 id 또는 상품 id를 다시 확인해주세요."));
        return PriceCategoryResponse.fromEntity(priceCategory);
    }

    public Long create(PriceCategoryRequest requestDTO) {
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "상품 id를 다시 확인해주세요."));

        try {
            PriceCategory priceCategory = requestDTO.toEntity(product);
            priceCategoryRepository.save(priceCategory);
            return priceCategory.getId();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "가격 카테고리", ErrorCode.CREATION_FAILURE);
        }
    }

    public void delete(Long productId, Long categoryId) {
        PriceCategory priceCategory = priceCategoryRepository.findByIdAndProductId(categoryId, productId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "가격 카테고리 id 또는 상품 id를 다시 확인해주세요."));

        try {
            priceCategoryRepository.delete(priceCategory);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "가격 카테고리", ErrorCode.DELETION_FAILURE);
        }
    }

    public Long updateStock(Long productId, Long categoryId, int stock) {
        PriceCategory priceCategory = priceCategoryRepository.findByIdAndProductId(categoryId, productId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "가격 카테고리 id 또는 상품 id를 다시 확인해주세요."));

        try {
            priceCategory.updateStock(stock);
            return priceCategory.getId();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "가격 카테고리", ErrorCode.UPDATE_FAILURE);
        }
    }

}
