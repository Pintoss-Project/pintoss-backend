package pintoss.giftmall.domains.product.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.product.dto.PriceCategoryResponse;
import pintoss.giftmall.domains.product.service.PriceCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Validated
public class PriceCategoryController {

    private final PriceCategoryService priceCategoryService;

    @GetMapping("/{id}/category")
    public ApiResponse<List<PriceCategoryResponse>> getAllPriceCategory(@PathVariable("id") @NotNull Long productId) {
        List<PriceCategoryResponse> priceCategoryList = priceCategoryService.findAllByProductId(productId);
        return ApiResponse.ok(priceCategoryList);
    }

    @GetMapping("/{id}/category/{category_id}")
    public ApiResponse<PriceCategoryResponse> getPriceCategoryById(@PathVariable("id") @NotNull Long productId, @PathVariable("category_id") @NotNull Long categoryId) {
        PriceCategoryResponse priceCategory = priceCategoryService.findByIdAndProductId(categoryId, productId);
        return ApiResponse.ok(priceCategory);
    }

}
