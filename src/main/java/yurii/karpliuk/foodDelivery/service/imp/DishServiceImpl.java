package yurii.karpliuk.foodDelivery.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.dto.request.DishAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.DishSearchRequest;
import yurii.karpliuk.foodDelivery.dto.response.DishResponse;
import yurii.karpliuk.foodDelivery.dto.response.MessageResponse;
import yurii.karpliuk.foodDelivery.entity.Dish;
import yurii.karpliuk.foodDelivery.entity.DishCategory;
import yurii.karpliuk.foodDelivery.entity.DishPhoto;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.repository.DishRepository;
import yurii.karpliuk.foodDelivery.repository.OrderItemRepository;
import yurii.karpliuk.foodDelivery.repository.spec.DishSpecification;
import yurii.karpliuk.foodDelivery.service.DishCategoryService;
import yurii.karpliuk.foodDelivery.service.DishService;
import yurii.karpliuk.foodDelivery.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private DishCategoryService dishCategoryService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public ResponseEntity<?> addDish(DishAddRequest dishAddRequest) {
        boolean isDishCreated = dishRepository.findAll().stream().anyMatch(d -> d.getDishName().equalsIgnoreCase(dishAddRequest.getDishName()));
        if (!isDishCreated) {
            Dish dish = new Dish();
            dish.setDishName(dishAddRequest.getDishName());
            dish.setDescription(dishAddRequest.getDescription());
            dish.setWeight(dishAddRequest.getWeight());
            dish.setPrice(dishAddRequest.getPrice());
            dish.setRestaurant(restaurantService.getOneById(dishAddRequest.getRestaurantId()));
            List<DishCategory> dishCategories = new ArrayList<>();
            dishCategories.add(dishCategoryService.findDishCategoryById(dishAddRequest.getDishCategoryId()));
            dish.setDishCategories(dishCategories);
            dishRepository.save(dish);
            log.info("In addDish DishService - added dish : '{}'", dish);
            return ResponseEntity.ok(new MessageResponse("Dish added successfully!"));
        } else {
            log.info("In addDish DishService - dish : '{}' was exist", dishAddRequest);
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Dish is already created!"));
        }
    }


    @Override
    public ResponseEntity<DishResponse> getOneById(Long id) {
        return  ResponseEntity.ok(buildDishResponse(dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dish with id: " + id + " does not exist"))));
    }

    @Override
    public ResponseEntity<?> updateDish(DishAddRequest dishAddRequest, Long id) {
        Dish updatedDish = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dish not found for this id :: " + id));
        updatedDish.setDishName(dishAddRequest.getDishName());
        /*   updatedDish.setPhotoUrl(dishAddRequest.getPhotoUrl());*/
        updatedDish.setPrice(dishAddRequest.getPrice());
        updatedDish.setWeight(dishAddRequest.getWeight());
        updatedDish.setDescription(dishAddRequest.getDescription());
        log.info("Update dish in DishService with dishName: {}, price{}, " +
                "weight: {} " + " description: {}", updatedDish.getDishName(), updatedDish.getPrice(), updatedDish.getWeight(), updatedDish.getDescription());
        dishRepository.save(updatedDish);
        return ResponseEntity.ok(new MessageResponse("Dish updated successfully!"));
    }

    @Override
    public ResponseEntity<?> deleteDish(Long dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new NotFoundException("Dish"));
//        Set<OrderItem> orderItems = dish.getOrderItems();
//        for(OrderItem orderItem:orderItems){
//            orderItem.getDishes().remove(dish);
//            orderItemRepository.save(orderItem);
//        }
        log.info("In deleteDish AdminService - delete dish id: '{}'", dishId);
        dishRepository.delete(dish);
        return ResponseEntity.ok(new MessageResponse("Dish deleted successfully!"));
    }

    @Override
    public ResponseEntity<List<DishResponse>> search(DishSearchRequest dishSearchRequest) {
        List<DishResponse> dishResponses = new ArrayList<>();
        DishSpecification dishSpecification = new DishSpecification(dishSearchRequest);
        PageRequest pageRequest = PageRequest.of(dishSearchRequest.getPageNumber(), dishSearchRequest.getPageSize(), Sort.by(Sort.Direction.ASC, dishSearchRequest.getSortBy()));
        List<Dish> dishes = dishRepository.findAll(dishSpecification, pageRequest).getContent();
        for (Dish dish : dishes) {
            dishResponses.add(buildDishResponse(dish));
        }
        return ResponseEntity.ok(dishResponses);
    }

    @Override
    public DishResponse buildDishResponse(Dish dish) {
        DishResponse response = new DishResponse();
        response.setDishName(dish.getDishName());
        response.setDescription(dish.getDescription());
        response.setPrice(dish.getPrice());
        response.setWeight(dish.getWeight());
        response.setRestaurantName(dish.getRestaurant().getNameOfRestaurant());
        response.setDishCategoriesNames(dish.getDishCategories().stream()
                .map(DishCategory::getName)
                .collect(Collectors.toList()));
        response.setImagesUrl(dish.getPhotos().stream().map(DishPhoto::getImgUrl).collect(Collectors.toList()));
        return response;
    }
}
