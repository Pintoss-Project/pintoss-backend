package pintoss.giftmall.domains.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.product.dto.ProductResponseDTO;
import pintoss.giftmall.domains.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponseDTO>> getAllProducts(@RequestParam(value = "category", required = false) String category) {
        if (category != null) {
            List<ProductResponseDTO> products = productService.findByCategory(category);
            return ApiResponse.ok(products);
        }
        List<ProductResponseDTO> products = productService.findAll();
        return ApiResponse.ok(products);
    }

    @GetMapping("/popular")
    public ApiResponse<List<ProductResponseDTO>> getPopularProducts() {
        List<ProductResponseDTO> popularProducts = productService.findPopularProducts();
        return ApiResponse.ok(popularProducts);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.findById(id);
        return ApiResponse.ok(product);
    }

}
