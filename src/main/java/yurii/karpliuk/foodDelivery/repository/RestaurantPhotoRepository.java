package yurii.karpliuk.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yurii.karpliuk.foodDelivery.entity.RestaurantPhoto;

@Repository
public interface RestaurantPhotoRepository extends JpaRepository<RestaurantPhoto,Long> {
}
