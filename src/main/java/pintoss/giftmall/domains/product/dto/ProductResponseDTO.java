package pintoss.giftmall.domains.product.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.domains.product.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ProductResponseDTO {

    private Long id;
    private String name;
    private boolean isPopular;
    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;
    private String homePage;
    private String csCenter;
    private String description;
    private String publisher;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ProductResponseDTO(Long id, String name, boolean isPopular, BigDecimal cardDiscount, BigDecimal phoneDiscount, String homePage, String csCenter, String description, String publisher, String category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.isPopular = isPopular;
        this.cardDiscount = cardDiscount;
        this.phoneDiscount = phoneDiscount;
        this.homePage = homePage;
        this.csCenter = csCenter;
        this.description = description;
        this.publisher = publisher;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ProductResponseDTO(Product product) {
    }

    public static ProductResponseDTO fromEntity(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .isPopular(product.isPopular())
                .cardDiscount(product.getCardDiscount())
                .phoneDiscount(product.getPhoneDiscount())
                .homePage(product.getHomePage())
                .csCenter(product.getCsCenter())
                .description(product.getDescription())
                .publisher(product.getPublisher())
                .category(product.getCategory())
                .build();
    }

}
