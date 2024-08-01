package pintoss.giftmall.domains.product.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.product.domain.Product;

@Component
@RequiredArgsConstructor
public class ProductReader {

    private final ProductRepository productRepository;

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("상품 id를 다시 확인해주세요."));
    }

}
