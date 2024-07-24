package pintoss.giftmall.domains.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.product.dto.PriceCategoryResponseDTO;
import pintoss.giftmall.domains.product.service.PriceCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class PriceCategoryController {

    private final PriceCategoryService priceCategoryService;

    @GetMapping("/{id}/category")
    public ApiResponse<List<PriceCategoryResponseDTO>> getAllPriceCategory(@PathVariable("id") Long productId) {
        List<PriceCategoryResponseDTO> priceCategoryList = priceCategoryService.findAllByProductId(productId);
        return ApiResponse.ok(priceCategoryList);
    }

    @GetMapping("/{id}/category/{category_id}")
    public ApiResponse<PriceCategoryResponseDTO> getPriceCategoryById(@PathVariable("id") Long productId, @PathVariable("category_id") Long categoryId) {
        PriceCategoryResponseDTO priceCategory = priceCategoryService.findByIdAndProductId(categoryId, productId);
        return ApiResponse.ok(priceCategory);
    }

}
