package pintoss.giftmall.domains.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.dto.ProductRequest;
import pintoss.giftmall.domains.product.dto.ProductResponse;
import pintoss.giftmall.domains.product.infra.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "상품을 찾을 수 없습니다.");
        }
        return products.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "상품 id를 다시 확인해주세요."));
        return ProductResponse.fromEntity(product);
    }

    public Long create(ProductRequest requestDTO) {
        try {
            Product product = requestDTO.toEntity();
            productRepository.save(product);
            return product.getId();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "상품", ErrorCode.CREATION_FAILURE);
        }
    }

    public Long update(Long id, ProductRequest requestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "상품 id를 다시 확인해주세요."));

        try {
            product.update(requestDTO);
            return product.getId();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "상품", ErrorCode.UPDATE_FAILURE);
        }
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "상품", ErrorCode.DELETION_FAILURE);
        }
    }

    public Long updateDiscount(Long id, BigDecimal discount, String type) {
        try {
            return productRepository.updateDiscount(id, discount, type);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "상품 할인", ErrorCode.UPDATE_FAILURE);
        }
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "카테고리에 해당하는 상품을 찾을 수 없습니다.");
        }
        return products.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findPopularProducts() {
        List<Product> products = productRepository.findByIsPopularTrue();
        if (products.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "인기 상품을 찾을 수 없습니다.");
        }
        return products.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
