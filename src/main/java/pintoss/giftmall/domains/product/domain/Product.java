package pintoss.giftmall.domains.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;
import pintoss.giftmall.domains.order.domain.OrderProduct;
import pintoss.giftmall.domains.product.dto.ProductRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(length = 50)
    private String name;

    private boolean isPopular;

    @Setter
    private BigDecimal cardDiscount;
    @Setter
    private BigDecimal phoneDiscount;

    @Column(length = 100)
    private String homePage;

    @Column(length = 20)
    private String csCenter;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 30)
    private String publisher;

    @Column(length = 20)
    private String category;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Product(String name, boolean isPopular, BigDecimal cardDiscount, BigDecimal phoneDiscount, String homePage, String csCenter, String description, String publisher, String category) {
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

    public void update(ProductRequest requestDTO) {
        if (StringUtils.hasText(requestDTO.getName())) this.name = requestDTO.getName();
        this.isPopular = requestDTO.isPopular();
        this.cardDiscount = requestDTO.getCardDiscount();
        this.phoneDiscount = requestDTO.getPhoneDiscount();
        if (StringUtils.hasText(requestDTO.getHomePage())) this.homePage = requestDTO.getHomePage();
        if (StringUtils.hasText(requestDTO.getCsCenter())) this.csCenter = requestDTO.getCsCenter();
        if (StringUtils.hasText(requestDTO.getDescription())) this.description = requestDTO.getDescription();
        if (StringUtils.hasText(requestDTO.getPublisher())) this.publisher = requestDTO.getPublisher();
        if (StringUtils.hasText(requestDTO.getCategory())) this.category = requestDTO.getCategory();
    }

    public void setDiscountPolicy(BigDecimal discount, String type) {
        switch (type.toLowerCase()) {
            case "card":
                this.cardDiscount = discount;
                break;
            case "phone":
                this.phoneDiscount = discount;
                break;
            default:
                throw new IllegalArgumentException("잘못된 타입입니다.");
        }
    }

}
