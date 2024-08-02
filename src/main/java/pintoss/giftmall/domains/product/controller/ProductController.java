package pintoss.giftmall.domains.product.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.product.dto.ProductResponse;
import pintoss.giftmall.domains.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(@RequestParam(value = "category", required = false) @NotNull String category) {
        if (category != null) {
            List<ProductResponse> products = productService.findByCategory(category);
            return ApiResponse.ok(products);
        }
        List<ProductResponse> products = productService.findAll();
        return ApiResponse.ok(products);
    }

    @GetMapping("/popular")
    public ApiResponse<List<ProductResponse>> getPopularProducts() {
        List<ProductResponse> popularProducts = productService.findPopularProducts();
        return ApiResponse.ok(popularProducts);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.findById(id);
        return ApiResponse.ok(product);
    }

}
