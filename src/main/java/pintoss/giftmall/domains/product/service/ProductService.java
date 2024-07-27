package pintoss.giftmall.domains.product.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.dto.ProductRequest;
import pintoss.giftmall.domains.product.dto.ProductResponse;
import pintoss.giftmall.domains.product.infra.ProductRepository;

import java.lang.reflect.Field;
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
        return productRepository.findAll().stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("product_id: " + id));
        return ProductResponse.fromEntity(product);
    }

    public Long create(ProductRequest requestDTO) {
        Product product = requestDTO.toEntity();
        productRepository.save(product);
        return product.getId();
    }

    public Long update(Long id, ProductRequest requestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("product_id: " + id));

        product.update(requestDTO);

        return product.getId();
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Long updateDiscount(Long id, BigDecimal discount, String type) {
        return productRepository.updateDiscount(id, discount, type);
    }

    public List<ProductResponse> findByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findPopularProducts() {
        return productRepository.findByIsPopularTrue().stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
