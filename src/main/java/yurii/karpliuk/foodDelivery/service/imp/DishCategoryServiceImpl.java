package yurii.karpliuk.foodDelivery.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.dto.request.DishCategoryAddRequest;
import yurii.karpliuk.foodDelivery.entity.DishCategory;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.repository.DishCategoryRepository;
import yurii.karpliuk.foodDelivery.service.DishCategoryService;

@Slf4j
@Service
public class
DishCategoryServiceImpl implements DishCategoryService {
    @Autowired
    DishCategoryRepository dishCategoryRepository;


    @Override
    public DishCategory findDishCategoryById(Long dishCategoryId) {
        return dishCategoryRepository.findById(dishCategoryId)
                .orElseThrow(() -> new NotFoundException("Dish Category with id:" + dishCategoryId));
    }

//    @Override
//    public void addDishCategory(DishCategoryAddRequest dishCategoryAddRequest) {
//        boolean isDishCategoryCreated = dishCategoryRepository.findAll().stream().anyMatch(dc -> dc.getName().equalsIgnoreCase(dishCategoryAddRequest.getName()));
//        if (!isDishCategoryCreated) {
//            DishCategory dishCategory = new DishCategory();
//            dishCategory.setName(dishCategoryAddRequest.getName());
//            dishCategory.setParentDishCategory(dishCategoryRepository.findById(dishCategoryAddRequest.getParentDishCategoryId()));
//            dishCategoryRepository.save(dishCategory);
//            log.info("In addDishCategory DishCategoryService - added dish Category: '{}'", dishCategory);
//        } else {
//            log.info("In addDishCategory DishCategoryService - dish Category: '{}' was exist", dishCategoryAddRequest);
//        }
//    }
}
