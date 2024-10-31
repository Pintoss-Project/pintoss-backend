package pintoss.giftmall.domains.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pintoss.giftmall.common.enums.ProductCategory;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.domain.ProductImage;
import pintoss.giftmall.domains.product.dto.*;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.infra.ProductImageRepository;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.product.infra.ProductReader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReader productReader;
    private final PriceCategoryRepository priceCategoryRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAllByIndexOrder();

        if (products.isEmpty()) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }

        return getProductResponses(products);
    }

    @Transactional(readOnly = true)
    public List<SimpleProductResponse> findAllSimpleProduct() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> {
                    Optional<ProductImage> logoImage = productImageRepository.findByProductId(product.getId());
                    String logoImageUrl = logoImage.map(ProductImage::getUrl).orElse(null);
                    return SimpleProductResponse.fromEntity(product, logoImageUrl);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productReader.findById(id);

        List<PriceCategory> priceCategories = priceCategoryRepository.findAllByProductId(id);
        Optional<ProductImage> logoImage = productImageRepository.findByProductId(id);
        String logoImageUrl = logoImage.map(ProductImage::getUrl).orElse(null);

        return ProductResponse.fromEntity(product, priceCategories, logoImageUrl);
    }

    public Long create(ProductRequest requestDTO) {
        int nextIndex = productRepository.findMaxIndex() + 1; // 현재 최대 인덱스의 다음 값으로 설정
        Product product = requestDTO.toEntity();
        product.changeIndex(nextIndex);// 자동 증가된 인덱스를 설정
        productRepository.save(product);

        if (StringUtils.hasText(requestDTO.getLogoImageUrl())) {
            ProductImage productImage = new ProductImage(requestDTO.getLogoImageUrl(), product);
            productImageRepository.save(productImage);
        }

        return product.getId();
    }

    public Long update(Long id, ProductRequest requestDTO) {
        Product product = productReader.findById(id);
        product.update(requestDTO);

        Optional<ProductImage> existingImage = productImageRepository.findByProductId(id);
        if (StringUtils.hasText(requestDTO.getLogoImageUrl())) {
            if (existingImage.isPresent()) {
                existingImage.get().updateLogoImage(requestDTO.getLogoImageUrl());
            } else {
                ProductImage newProductImage = new ProductImage(requestDTO.getLogoImageUrl(), product);
                productImageRepository.save(newProductImage);
            }
        } else if (existingImage.isPresent()) {
            productImageRepository.delete(existingImage.get());
        }

        return product.getId();
    }

    public void delete(Long id) {
        List<PriceCategory> priceCategories = priceCategoryRepository.findAllByProductId(id);
        if (!priceCategories.isEmpty()) {
            priceCategoryRepository.deleteAll(priceCategories);
        }

        Optional<ProductImage> productImage = productImageRepository.findByProductId(id);
        productImage.ifPresent(productImageRepository::delete);

        productRepository.deleteById(id);
    }

    public Long updateDiscount(Long id, UpdateDiscountRequest updateDiscountRequest) {
        return productRepository.updateDiscount(id, updateDiscountRequest);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategory(ProductCategory category) {
        List<Product> products = productRepository.findByCategory(category);

        return getProductResponses(products);
    }

    @Transactional(readOnly = true)
    public List<SimpleProductResponse> findSimpleByCategory(ProductCategory category) {
        List<Product> products = productRepository.findByCategory(category);

        return products.stream()
                .map(product -> {
                    Optional<ProductImage> logoImage = productImageRepository.findByProductId(product.getId());
                    String logoImageUrl = logoImage.map(ProductImage::getUrl).orElse(null);
                    return SimpleProductResponse.fromEntity(product, logoImageUrl);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SimpleProductResponse> findPopularProducts() {
        List<Product> products = productRepository.findByIsPopularTrue();

        return products.stream()
                .map(product -> {
                    Optional<ProductImage> logoImage = productImageRepository.findByProductId(product.getId());
                    String logoImageUrl = logoImage.map(ProductImage::getUrl).orElse(null);
                    return SimpleProductResponse.fromEntity(product, logoImageUrl);
                })
                .collect(Collectors.toList());
    }

    public String getProductLogoImageUrl(Long productId) {
        return productImageRepository.findByProductId(productId).stream()
                .findFirst()
                .map(ProductImage::getUrl)
                .orElse(null);
    }
    
    //상품권 순서 변경
    public void reorderProducts(List<Long> productIdsInNewOrder) {
        for (int i = 0; i < productIdsInNewOrder.size(); i++) {
            log.info(productIdsInNewOrder.size());
            Product product = productRepository.findById(productIdsInNewOrder.get(i))
                    .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));
            log.info(productIdsInNewOrder.get(i));
            product.changeIndex(i + 1); // changeIndex 메서드를 통해 인덱스 값 변경
            productRepository.save(product);  // 변경된 엔티티를 저장
        }
    }

    private List<ProductResponse> getProductResponses(List<Product> products) {
        return products.stream()
                .map(product -> {
                    List<PriceCategory> priceCategories = priceCategoryRepository.findAllByProductId(product.getId());
                    Optional<ProductImage> logoImage = productImageRepository.findByProductId(product.getId());
                    String logoImageUrl = logoImage.map(ProductImage::getUrl).orElse(null);
                    return ProductResponse.fromEntity(product, priceCategories, logoImageUrl);
                })
                .collect(Collectors.toList());
    }

}
