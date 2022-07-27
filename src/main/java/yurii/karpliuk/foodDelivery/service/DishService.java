package yurii.karpliuk.foodDelivery.service;

import org.springframework.http.ResponseEntity;
import yurii.karpliuk.foodDelivery.dto.request.DishAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.DishSearchRequest;
import yurii.karpliuk.foodDelivery.dto.response.DishResponse;
import yurii.karpliuk.foodDelivery.entity.Dish;

import java.util.List;

public interface DishService {
    ResponseEntity<?> addDish(DishAddRequest dishAddRequest);

    ResponseEntity<DishResponse> getOneById(Long id);

    ResponseEntity<?> updateDish(DishAddRequest dishAddRequest,Long id);

    ResponseEntity<?> deleteDish(Long id);

    ResponseEntity<List<DishResponse>> search(DishSearchRequest dishSearchRequest);
    DishResponse buildDishResponse(Dish dish);
}
