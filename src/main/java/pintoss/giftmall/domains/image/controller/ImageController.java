package pintoss.giftmall.domains.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pintoss.giftmall.common.responseobj.ApiResponse;
import pintoss.giftmall.domains.image.dto.ImageResponse;
import pintoss.giftmall.domains.image.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ApiResponse<ImageResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageResponse imageResponse = imageService.uploadImage(file);
        return ApiResponse.ok(imageResponse);
    }

    @PostMapping("/uploads")
    public ApiResponse<List<ImageResponse>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        List<ImageResponse> imageResponses = imageService.uploadImages(files);
        return ApiResponse.ok(imageResponses);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ApiResponse.ok(null);
    }

}
