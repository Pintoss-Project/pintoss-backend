package pintoss.giftmall.domains.product.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.product.dto.PriceCategoryRequest;
import pintoss.giftmall.domains.product.service.PriceCategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
@Validated
public class PriceCategoryAdminController {

    private final PriceCategoryService priceCategoryService;

    @PostMapping("/{id}/category")
    public ApiResponse<List<Long>> createPriceCategory(
            @RequestBody @Valid List<PriceCategoryRequest> requestDTOs) {

        List<Long> createdPriceCategoryIds = requestDTOs.stream()
                .map(priceCategoryService::create)
                .collect(Collectors.toList());

        return ApiResponse.ok(createdPriceCategoryIds);
    }

    @DeleteMapping("/{id}/category/{category_id}")
    public ApiResponse<String> deletePriceCategory(@PathVariable(name = "id") Long id, @PathVariable(name = "category_id") Long categoryId) {
        priceCategoryService.delete(id, categoryId);
        return ApiResponse.ok("가격 카테고리가 삭제되었습니다.");
    }

    @PatchMapping("/{id}/category/{category_id}/stock")
    public ApiResponse<Long> updateStock(@PathVariable(name = "id") Long id, @PathVariable(name = "category_id") Long categoryId, @RequestBody @NotNull int stock) {
        Long updatedPriceCategoryId = priceCategoryService.updateStock(id, categoryId, stock);
        return ApiResponse.ok(updatedPriceCategoryId);
    }

}
