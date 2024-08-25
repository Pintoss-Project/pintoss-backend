package pintoss.giftmall.domains.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.image.domain.Image;
import pintoss.giftmall.domains.image.dto.ImageResponse;
import pintoss.giftmall.domains.image.infra.ImageReader;
import pintoss.giftmall.domains.image.infra.ImageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageReader imageReader;

    @Transactional
    public List<ImageResponse> saveImageUrls(List<String> imageUrls) {
        List<ImageResponse> imageResponses = new ArrayList<>();

        for (String url : imageUrls) {
            Image image = new Image(url);
            Image savedImage = imageRepository.save(image);
            imageResponses.add(new ImageResponse(savedImage.getId(), savedImage.getUrl()));
        }

        return imageResponses;
    }

    @Transactional
    public void deleteImage(Long id) {
        Image image = imageReader.findById(id);
        imageRepository.delete(image);
    }

}
