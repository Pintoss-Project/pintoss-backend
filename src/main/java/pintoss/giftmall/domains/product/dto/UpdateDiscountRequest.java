package pintoss.giftmall.domains.product.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateDiscountRequest {

    private BigDecimal cardDiscount;
    private BigDecimal phoneDiscount;

}
