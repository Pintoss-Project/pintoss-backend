package pintoss.giftmall.domains.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.common.enums.ProductCategory;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductResponse {

    private Long id;
    private String name;
    @JsonProperty("isPopular")
    private boolean isPopular;
    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;
    private String homePage;
    private String csCenter;
    private String description;
    private String publisher;
    private ProductCategory category;
    private String logoImageUrl;
    private String note;
    private int index;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PriceCategoryResponse> priceCategories;

    @Builder
    public ProductResponse(Long id, String name, boolean isPopular, BigDecimal cardDiscount, BigDecimal phoneDiscount, String homePage, String csCenter, String description, String publisher, ProductCategory category, String logoImageUrl, String note, int index, LocalDateTime createdAt, LocalDateTime updatedAt, List<PriceCategoryResponse> priceCategories) {
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
        this.logoImageUrl = logoImageUrl;
        this.note = note;
        this.index = index;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.priceCategories = priceCategories;
    }

    public ProductResponse(Product product) {
    }

    public static ProductResponse fromEntity(Product product, List<PriceCategory> priceCategoryList, String logoImageUrl) {
        List<PriceCategoryResponse> priceCategoryResponses = priceCategoryList.stream()
                .map(PriceCategoryResponse::fromEntity)
                .collect(Collectors.toList());

        return ProductResponse.builder()
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
                .logoImageUrl(logoImageUrl)
                .note(product.getNote())
                .index(product.getIndex())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .priceCategories(priceCategoryResponses)
                .build();
    }
}
