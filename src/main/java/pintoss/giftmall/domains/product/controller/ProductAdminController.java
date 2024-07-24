package pintoss.giftmall.domains.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.product.dto.ProductRequestDTO;
import pintoss.giftmall.domains.product.dto.ProductResponseDTO;
import pintoss.giftmall.domains.product.service.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO createdProduct = productService.create(requestDTO);
        return ApiResponse.ok(createdProduct);
    }

    @PatchMapping("/{id}")
    public ApiResponse<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO updatedProduct = productService.update(id, requestDTO);
        return ApiResponse.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.ok("상품권 삭제가 완료되었습니다.");
    }

    @PatchMapping("/{id}/fee")
    public ApiResponse<ProductResponseDTO> updateProductFee(@PathVariable Long id, @RequestParam BigDecimal discount, @RequestParam String type) {
        ProductResponseDTO updatedProduct = productService.updateDiscount(id, discount, type);
        return ApiResponse.ok(updatedProduct);
    }

}
