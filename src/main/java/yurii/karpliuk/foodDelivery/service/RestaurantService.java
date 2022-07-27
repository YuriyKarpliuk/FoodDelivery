package yurii.karpliuk.foodDelivery.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import yurii.karpliuk.foodDelivery.dto.request.RestaurantAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.RestaurantSearchRequest;
import yurii.karpliuk.foodDelivery.entity.Restaurant;

import java.util.List;

public interface RestaurantService {
    ResponseEntity<?> addRestaurant(RestaurantAddRequest restaurantAddRequest);

    Page<Restaurant> findAllRestaurants(Integer pageNumber, Integer pageSize, String sortBy);

    ResponseEntity<List<Restaurant>> searchByNameOfRestaurant(String nameOfRestaurant, Integer pageNumber, Integer pageSize, String sortBy);

    ResponseEntity<List<Restaurant>> search(RestaurantSearchRequest restaurantSearchRequest);

    Restaurant getOneById(Long id);

    ResponseEntity<?> updateRestaurant(RestaurantAddRequest restaurantAddRequest, Long id);

    ResponseEntity<?> deleteRestaurant(Long id);
}
