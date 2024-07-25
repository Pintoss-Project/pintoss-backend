package pintoss.giftmall.domains.product.infra;

import pintoss.giftmall.domains.product.domain.Product;

import java.math.BigDecimal;

public interface ProductRepositoryCustom {

    Long updateDiscount(Long id, BigDecimal cardDiscount, String type);

}
