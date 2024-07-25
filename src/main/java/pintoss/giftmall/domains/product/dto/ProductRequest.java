package pintoss.giftmall.domains.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pintoss.giftmall.domains.product.domain.Product;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

    @NotBlank
    private String name;

    @NotNull
    private boolean isPopular = false;

    @NotNull
    private BigDecimal cardDiscount = BigDecimal.ZERO;

    @NotNull
    private BigDecimal phoneDiscount = BigDecimal.ZERO;

    @NotBlank
    private String homePage;

    @NotBlank
    private String csCenter;

    @NotBlank
    private String description;

    @NotBlank
    private String publisher;

    @NotBlank
    private String category;

    @Builder
    public ProductRequest(String name, boolean isPopular, BigDecimal cardDiscount, BigDecimal phoneDiscount,
                          String homePage, String csCenter, String description, String publisher, String category) {
        this.name = name;
        this.isPopular = isPopular;
        this.cardDiscount = cardDiscount;
        this.phoneDiscount = phoneDiscount;
        this.homePage = homePage;
        this.csCenter = csCenter;
        this.description = description;
        this.publisher = publisher;
        this.category = category;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .isPopular(isPopular)
                .cardDiscount(cardDiscount)
                .phoneDiscount(phoneDiscount)
                .homePage(homePage)
                .csCenter(csCenter)
                .description(description)
                .publisher(publisher)
                .category(category)
                .build();
    }

}
