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
    public ApiResponse<Long> createProduct(@RequestBody ProductRequestDTO requestDTO) {
        Long createdProductId = productService.create(requestDTO);
        return ApiResponse.ok(createdProductId);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO requestDTO) {
        Long updatedProductId = productService.update(id, requestDTO);
        return ApiResponse.ok(updatedProductId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.ok("상품권 삭제가 완료되었습니다.");
    }

    @PatchMapping("/{id}/fee")
    public ApiResponse<Long> updateProductFee(@PathVariable Long id, @RequestParam BigDecimal discount, @RequestParam String type) {
        Long updatedProductId = productService.updateDiscount(id, discount, type);
        return ApiResponse.ok(updatedProductId);
    }

}
