package pintoss.giftmall.domains.image.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import pintoss.giftmall.domains.image.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
