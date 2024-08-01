package pintoss.giftmall.domains.product.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.product.dto.ProductRequest;
import pintoss.giftmall.domains.product.service.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
@Validated
public class ProductAdminController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<Long> createProduct(@RequestBody @Valid ProductRequest requestDTO) {
        Long createdProductId = productService.create(requestDTO);
        return ApiResponse.ok(createdProductId);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> updateProduct(@PathVariable @NotNull Long id, @RequestBody @Valid ProductRequest requestDTO) {
        Long updatedProductId = productService.update(id, requestDTO);
        return ApiResponse.ok(updatedProductId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable @NotNull Long id) {
        productService.delete(id);
        return ApiResponse.ok("상품권 삭제가 완료되었습니다.");
    }

    @PatchMapping("/{id}/fee")
    public ApiResponse<Long> updateProductFee(@PathVariable @NotNull Long id, @RequestParam @NotNull BigDecimal discount, @RequestParam @NotNull String type) {
        Long updatedProductId = productService.updateDiscount(id, discount, type);
        return ApiResponse.ok(updatedProductId);
    }

}
