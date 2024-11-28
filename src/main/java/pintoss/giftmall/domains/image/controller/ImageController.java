package pintoss.giftmall.domains.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.image.dto.ImageResponse;
import pintoss.giftmall.domains.image.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/save")
    public ApiResponse<List<ImageResponse>> saveImageUrls(@RequestBody List<String> imageUrls) {
        List<ImageResponse> imageResponses = imageService.saveImageUrls(imageUrls);
        return ApiResponse.ok(imageResponses);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteImage(@PathVariable(value = "id") Long id) {
        imageService.deleteImage(id);
        return ApiResponse.ok(null);
    }

}
