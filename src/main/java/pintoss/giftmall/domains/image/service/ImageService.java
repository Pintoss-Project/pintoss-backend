package pintoss.giftmall.domains.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pintoss.giftmall.common.exceptions.server.ImageUploadException;
import pintoss.giftmall.common.utils.S3Service;
import pintoss.giftmall.domains.image.domain.Image;
import pintoss.giftmall.domains.image.infra.ImageRepository;
import pintoss.giftmall.domains.image.dto.ImageResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @Transactional
    public ImageResponse uploadImage(MultipartFile file) throws ImageUploadException {
        String url = s3Service.upload(file);
        Image image = new Image(url);
        imageRepository.save(image);
        return new ImageResponse(image.getId(), image.getUrl());
    }

    @Transactional
    public List<ImageResponse> uploadImages(List<MultipartFile> files) throws ImageUploadException {
        return files.stream().map(this::uploadImage).collect(Collectors.toList());
    }

    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new ImageUploadException("이미지를 찾을 수 없습니다."));
        s3Service.deleteImageFromS3(image.getUrl());
        imageRepository.delete(image);
    }

}
