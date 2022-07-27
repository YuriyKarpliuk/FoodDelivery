package yurii.karpliuk.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import yurii.karpliuk.foodDelivery.entity.DishPhoto;

import java.util.List;

@Repository
public interface DishPhotoRepository extends JpaRepository<DishPhoto,Long> , JpaSpecificationExecutor<DishPhoto> {
    List<DishPhoto> findByDishId(Long dishId);
}
