package pintoss.giftmall.domains.product.infra;

import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.dto.UpdateDiscountRequest;

import java.math.BigDecimal;

public interface ProductRepositoryCustom {

    Long updateDiscount(Long id, UpdateDiscountRequest updateDiscountRequest);

}
