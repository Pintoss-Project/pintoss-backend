package pintoss.giftmall.domains.product.service.port;

import pintoss.giftmall.domains.product.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByCategory(String category);

    List<Product> findByIsPopular(boolean isPopular);

    Product register(Product product);

    Product update(Product product);

    void deleteById(Long id);

    Product updateDiscount(Long id, BigDecimal cardDiscount, String type);

}
