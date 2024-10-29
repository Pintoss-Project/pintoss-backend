package pintoss.giftmall.domains.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.enums.ProductCategory;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.dto.*;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.product.infra.ProductReader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReader productReader;
    private final PriceCategoryRepository priceCategoryRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }

        return products.stream()
                .map(product -> {
                    List<PriceCategory> priceCategories = priceCategoryRepository
                            .findAllByProductId(product.getId());
                    return ProductResponse.fromEntity(product, priceCategories);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SimpleProductResponse> findAllSimpleProduct() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(SimpleProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productReader.findById(id);

        List<PriceCategory> priceCategories = priceCategoryRepository.findAllByProductId(id);
        List<PriceCategoryResponse> priceCategoryResponses = priceCategories.stream()
                .map(PriceCategoryResponse::fromEntity)
                .toList();

        return ProductResponse.fromEntity(product, priceCategories);
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
        List<PriceCategory> priceCategories = priceCategoryRepository.findAllByProductId(id);
        if (!priceCategories.isEmpty()) {
            priceCategoryRepository.deleteAll(priceCategories);
        }

        productRepository.deleteById(id);
    }

    public Long updateDiscount(Long id, UpdateDiscountRequest updateDiscountRequest) {
        return productRepository.updateDiscount(id, updateDiscountRequest);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategory(ProductCategory category) {
        List<Product> products = productRepository.findByCategory(category);

        return products.stream()
                .map(product -> {
                    List<PriceCategory> priceCategories = priceCategoryRepository.findAllByProductId(product.getId());
                    return ProductResponse.fromEntity(product, priceCategories);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SimpleProductResponse> findSimpleByCategory(ProductCategory category) {
        List<Product> products = productRepository.findByCategory(category);

        return products.stream()
                .map(SimpleProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SimpleProductResponse> findPopularProducts() {
        List<Product> products = productRepository.findByIsPopularTrue();

        return products.stream()
                .map(SimpleProductResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    //상품권 순서 변경
    @Transactional
    public void reorderProducts(List<Long> productIdsInNewOrder) {
        for (int i = 0; i < productIdsInNewOrder.size(); i++) {
            Product product = productRepository.findById(productIdsInNewOrder.get(i))
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
            product.changeIndex(i); // changeIndex 메서드를 통해 인덱스 값 변경
        }
    }
}
