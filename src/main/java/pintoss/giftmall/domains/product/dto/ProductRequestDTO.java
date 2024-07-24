package pintoss.giftmall.domains.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pintoss.giftmall.domains.product.domain.Product;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDTO {

    private String name;
    private boolean isPopular;
    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;
    private String homePage;
    private String csCenter;
    private String description;
    private String publisher;
    private String category;

    @Builder
    public ProductRequestDTO(String name, boolean isPopular, BigDecimal cardDiscount, BigDecimal phoneDiscount,
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
