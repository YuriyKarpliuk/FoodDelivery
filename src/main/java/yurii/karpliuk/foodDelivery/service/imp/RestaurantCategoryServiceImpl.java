package yurii.karpliuk.foodDelivery.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.entity.RestaurantCategory;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.repository.RestaurantCategoryRepository;
import yurii.karpliuk.foodDelivery.service.RestaurantCategoryService;

@Service
public class RestaurantCategoryServiceImpl implements RestaurantCategoryService {
    @Autowired
    RestaurantCategoryRepository restaurantCategoryRepository;
    @Override
    public RestaurantCategory findRestaurantCategoryById(Long restaurantCategoryId) {
        return restaurantCategoryRepository.findById(restaurantCategoryId)
                .orElseThrow(()-> new NotFoundException("Restaurant Category with id:"+restaurantCategoryId));
    }
}




