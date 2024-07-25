package pintoss.giftmall.domains.product.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.dto.ProductRequestDTO;
import pintoss.giftmall.domains.product.dto.ProductResponseDTO;
import pintoss.giftmall.domains.product.infra.ProductRepository;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("product_id: " + id));
        return ProductResponseDTO.fromEntity(product);
    }

    @Transactional
    public Long create(ProductRequestDTO requestDTO) {
        Product product = requestDTO.toEntity();
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public Long update(Long id, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("product_id: " + id));

        updateNonNullFields(requestDTO, product);

        return product.getId();
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Long updateDiscount(Long id, BigDecimal discount, String type) {
        return productRepository.updateDiscount(id, discount, type);
    }

    public List<ProductResponseDTO> findByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> findPopularProducts() {
        return productRepository.findByIsPopularTrue().stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private void updateNonNullFields(Object source, Object target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(source);
                if (value != null) {
                    Field targetField = target.getClass().getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                    targetField.set(target, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
