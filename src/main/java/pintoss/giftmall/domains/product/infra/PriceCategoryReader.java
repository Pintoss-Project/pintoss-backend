package pintoss.giftmall.domains.product.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.product.domain.PriceCategory;

@Component
@RequiredArgsConstructor
public class PriceCategoryReader {

    private final PriceCategoryRepository priceCategoryRepository;

    public PriceCategory findById(Long id) {
        return priceCategoryRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "가격 카테고리 id를 다시 확인해주세요."));
    }

    public PriceCategory findByIdAndProductId(Long categoryId, Long productId) {
        return priceCategoryRepository.findByIdAndProductId(categoryId, productId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "가격 카테고리 id 또는 상품 id를 다시 확인해주세요."));
    }

}
