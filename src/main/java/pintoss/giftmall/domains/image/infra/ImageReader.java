package pintoss.giftmall.domains.image.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.domains.image.domain.Image;

@Component
@RequiredArgsConstructor
public class ImageReader {

    private final ImageRepository imageRepository;

    public Image findById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("이미지 id를 다시 확인해주세요."));
    }
}
