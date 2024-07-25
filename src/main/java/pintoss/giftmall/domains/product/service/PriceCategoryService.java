package pintoss.giftmall.domains.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.dto.PriceCategoryRequestDTO;
import pintoss.giftmall.domains.product.dto.PriceCategoryResponseDTO;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceCategoryService {

    private final PriceCategoryRepository priceCategoryRepository;

    @Transactional(readOnly = true)
    public List<PriceCategoryResponseDTO> findAllByProductId(Long productId) {
        List<PriceCategory> priceCategories = priceCategoryRepository.findAllByProductId(productId);
        return priceCategories.stream()
                .map(PriceCategoryResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PriceCategoryResponseDTO findByIdAndProductId(Long categoryId, Long productId) {
        PriceCategory priceCategory = priceCategoryRepository.findByIdAndProductId(categoryId, productId)
                .orElseThrow(() -> new IllegalArgumentException("PriceCategory not found or does not belong to the given Product"));
        return PriceCategoryResponseDTO.fromEntity(priceCategory);
    }


    @Transactional
    public Long create(PriceCategoryRequestDTO requestDTO) {
        PriceCategory priceCategory = requestDTO.toEntity();
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

        priceCategory.setStock(stock);

        return priceCategory.getId();
    }

}
