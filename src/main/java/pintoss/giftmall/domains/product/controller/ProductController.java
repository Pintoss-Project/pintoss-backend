package pintoss.giftmall.domains.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.enums.ProductCategory;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.product.dto.ProductResponse;
import pintoss.giftmall.domains.product.dto.SimpleProductResponse;
import pintoss.giftmall.domains.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(@RequestParam(name = "category", value = "category", required = false) ProductCategory category) {
        if (category != null) {
            List<ProductResponse> products = productService.findByCategory(category);
            return ApiResponse.ok(products);
        }
        List<ProductResponse> products = productService.findAll();
        return ApiResponse.ok(products);
    }

    @GetMapping("/simple")
    public ApiResponse<List<SimpleProductResponse>> getAllSimpleProducts(@RequestParam(name = "category", value = "category", required = false) ProductCategory category) {
        if (category != null) {
            List<SimpleProductResponse> products = productService.findSimpleByCategory(category);
            return ApiResponse.ok(products);
        }

        List<SimpleProductResponse> products = productService.findAllSimpleProduct();
        return ApiResponse.ok(products);
    }

    @GetMapping("/popular")
    public ApiResponse<List<SimpleProductResponse>> getPopularProducts() {
        List<SimpleProductResponse> popularProducts = productService.findPopularProducts();
        return ApiResponse.ok(popularProducts);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable(name = "id") Long id) {
        ProductResponse product = productService.findById(id);
        return ApiResponse.ok(product);
    }

    //상품권 목록 순서 변경 api
    @PatchMapping("/reorder")
    public ApiResponse<String> updateProductOrder(@RequestBody List<Long> orderUpdateRequests) {
        productService.reorderProducts(orderUpdateRequests);
        return ApiResponse.ok("수정이 되었습니다.");
    }
}
