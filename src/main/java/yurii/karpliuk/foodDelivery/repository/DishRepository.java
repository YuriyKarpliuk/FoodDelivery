package yurii.karpliuk.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import yurii.karpliuk.foodDelivery.entity.Dish;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish,Long>, JpaSpecificationExecutor<Dish> {
    Optional<Dish> findByDishName(Dish dishName);
}
