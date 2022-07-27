package yurii.karpliuk.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yurii.karpliuk.foodDelivery.entity.Dish;
import yurii.karpliuk.foodDelivery.entity.DishCategory;

@Repository
public interface DishCategoryRepository extends JpaRepository<DishCategory,Long> {
}
