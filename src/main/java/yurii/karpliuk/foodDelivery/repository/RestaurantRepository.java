package yurii.karpliuk.foodDelivery.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yurii.karpliuk.foodDelivery.entity.Restaurant;
import yurii.karpliuk.foodDelivery.entity.RestaurantCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant>  {
   List<Restaurant> findAllByNameOfRestaurantLike(String nameOfRestaurant, Pageable pageable);

   @Query("select r from Restaurant r where r.nameOfRestaurant =?1")
   Restaurant searchByName(@Param("name") String nameOfRestaurant );
}
