package pintoss.giftmall.domains.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.dto.ProductRequest;
import pintoss.giftmall.domains.product.dto.ProductResponse;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.product.infra.ProductReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReader productReader;

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }

        return products.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productReader.findById(id);

        return ProductResponse.fromEntity(product);
    }

    public Long create(ProductRequest requestDTO) {
        Product product = requestDTO.toEntity();
        productRepository.save(product);

        return product.getId();
    }

    public Long update(Long id, ProductRequest requestDTO) {
        Product product = productReader.findById(id);
        product.update(requestDTO);

        return product.getId();
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Long updateDiscount(Long id, BigDecimal discount, String type) {
        return productRepository.updateDiscount(id, discount, type);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);

        return products.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findPopularProducts() {
        List<Product> products = productRepository.findByIsPopularTrue();

        return products.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
