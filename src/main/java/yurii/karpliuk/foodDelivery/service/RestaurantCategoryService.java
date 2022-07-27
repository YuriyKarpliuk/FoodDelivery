package yurii.karpliuk.foodDelivery.service;


import yurii.karpliuk.foodDelivery.entity.RestaurantCategory;

public interface RestaurantCategoryService {
    RestaurantCategory findRestaurantCategoryById(Long restaurantCategoryId);
}
