package pintoss.giftmall.domains.image.infra;

import pintoss.giftmall.domains.image.domain.Image;

import java.util.Optional;

public interface ImageRepository {

    void save(Image image);

    Optional<Image> findById(Long id);

    void delete(Image image);

    void deleteById(Long id);

}
