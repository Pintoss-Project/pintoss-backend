package pintoss.giftmall.domains.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.product.dto.PriceCategoryRequestDTO;
import pintoss.giftmall.domains.product.dto.PriceCategoryResponseDTO;
import pintoss.giftmall.domains.product.service.PriceCategoryService;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class PriceCategoryAdminController {

    private final PriceCategoryService priceCategoryService;

    @PostMapping("/{id}/category")
    public ApiResponse<Long> createPriceCategory(@RequestBody PriceCategoryRequestDTO requestDTO) {
        Long createdPriceCategoryId = priceCategoryService.create(requestDTO);
        return ApiResponse.ok(createdPriceCategoryId);
    }

    @DeleteMapping("/{id}/category/{category_id}")
    public ApiResponse<String> deletePriceCategory(@PathVariable Long id, @PathVariable Long category_id) {
        priceCategoryService.delete(id, category_id);
        return ApiResponse.ok("가격 카테고리가 삭제되었습니다.");
    }

    @PatchMapping("/{id}/category/{category_id}/stock")
    public ApiResponse<Long> updateStock(@PathVariable Long id, @PathVariable Long category_id, @RequestParam int stock) {
        Long updatedPriceCategoryId = priceCategoryService.updateStock(id, category_id, stock);
        return ApiResponse.ok(updatedPriceCategoryId);
    }

}
