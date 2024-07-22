package pintoss.giftmall.domains.image.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pintoss.giftmall.domains.image.domain.Image;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final ImageJpaRepository imageJpaRepository;

    @Override
    public void save(Image image) {
        imageJpaRepository.save(image);
    }

    @Override
    public Optional<Image> findById(Long id) {
        return imageJpaRepository.findById(id);
    }

    @Override
    public void delete(Image image) {
        imageJpaRepository.delete(image);
    }

    @Override
    public void deleteById(Long id) {
        imageJpaRepository.deleteById(id);
    }

}
