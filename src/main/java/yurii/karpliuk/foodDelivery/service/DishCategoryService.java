package yurii.karpliuk.foodDelivery.service;


import yurii.karpliuk.foodDelivery.dto.request.DishCategoryAddRequest;
import yurii.karpliuk.foodDelivery.entity.DishCategory;

public interface DishCategoryService {
    DishCategory findDishCategoryById(Long dishCategoryId);

//    void addDishCategory(DishCategoryAddRequest dishCategoryAddRequest);
}
