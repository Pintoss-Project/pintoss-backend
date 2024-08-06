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

import java.util.ArrayList;
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
        List<ImageResponse> imageResponses = new ArrayList<>();
        List<String> uploadedUrls = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                String url = s3Service.upload(file);
                uploadedUrls.add(url);
                Image image = new Image(url);
                imageRepository.save(image);
                imageResponses.add(new ImageResponse(image.getId(), image.getUrl()));
            }
        } catch (ImageUploadException e) {
            for (String url : uploadedUrls) {
                s3Service.deleteImageFromS3(url);
            }
            throw e;
        }

        return imageResponses;
    }

    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new ImageUploadException("이미지를 찾을 수 없습니다."));
        imageRepository.delete(image);
        s3Service.deleteImageFromS3(image.getUrl());
    }

}
