package yurii.karpliuk.foodDelivery.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.dto.request.RestaurantAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.RestaurantSearchRequest;
import yurii.karpliuk.foodDelivery.dto.response.MessageResponse;
import yurii.karpliuk.foodDelivery.entity.Restaurant;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.repository.AddressRepository;
import yurii.karpliuk.foodDelivery.repository.RestaurantCategoryRepository;
import yurii.karpliuk.foodDelivery.repository.RestaurantRepository;
import yurii.karpliuk.foodDelivery.repository.spec.RestaurantSpecification;
import yurii.karpliuk.foodDelivery.service.AddressService;
import yurii.karpliuk.foodDelivery.service.RestaurantCategoryService;
import yurii.karpliuk.foodDelivery.service.RestaurantService;

import java.util.List;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    RestaurantCategoryRepository restaurantCategoryRepository;
    @Autowired
    AddressService addressService;

    @Autowired
    RestaurantCategoryService restaurantCategoryService;

    @Override
    public ResponseEntity<?> addRestaurant(RestaurantAddRequest restaurantAddRequest) {
        boolean isRestaurantCreated = restaurantRepository.findAll().stream().anyMatch(r -> r.getNameOfRestaurant().equalsIgnoreCase(restaurantAddRequest.getNameOfRestaurant()));
        if (!isRestaurantCreated) {
            Restaurant restaurant = new Restaurant();
            restaurant.setNameOfRestaurant(restaurantAddRequest.getNameOfRestaurant());
            restaurant.setAddress(addressService.findAddressById(restaurantAddRequest.getAddressId()));
            restaurant.setDescription(restaurantAddRequest.getDescription());
            restaurant.setFreeDelivery(restaurantAddRequest.getFreeDelivery());
            restaurant.setRestaurantCategory(restaurantCategoryService.findRestaurantCategoryById(restaurantAddRequest.getRestaurantCategoryId()));
            restaurantRepository.save(restaurant);
            log.info("In addRestaurant RestaurantService - added restaurant: '{}'", restaurant);
            return ResponseEntity.ok(new MessageResponse("Restaurant added successfully!"));
        } else {
            log.info("In addRestaurant RestaurantService - restaurant: '{}' was exist", restaurantAddRequest);
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Restaurant is already created!"));
        }
    }

    @Override
    public Page<Restaurant> findAllRestaurants(Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return restaurantRepository.findAll(pageRequest);
    }

    @Override
    public ResponseEntity<List<Restaurant>> searchByNameOfRestaurant(String nameOfRestaurant, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return ResponseEntity.ok(restaurantRepository.findAllByNameOfRestaurantLike("%" + nameOfRestaurant + "%", pageRequest));
    }

    @Override
    public ResponseEntity<List<Restaurant>> search(RestaurantSearchRequest restaurantSearchRequest) {
        RestaurantSpecification restaurantSpecification = new RestaurantSpecification(restaurantSearchRequest);
        PageRequest pageRequest = PageRequest.of(restaurantSearchRequest.getPageNumber(), restaurantSearchRequest.getPageSize(), Sort.by(Sort.Direction.ASC, restaurantSearchRequest.getSortBy()));
        return ResponseEntity.ok(restaurantRepository.findAll(restaurantSpecification, pageRequest).getContent());
    }

    @Override
    public Restaurant getOneById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant with id: " + id + " does not exist"));
    }

    @Override
    public ResponseEntity<?> updateRestaurant(RestaurantAddRequest restaurantAddRequest, Long id) {
        Restaurant updatedRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found for this id :: " + id));
        updatedRestaurant.setNameOfRestaurant(restaurantAddRequest.getNameOfRestaurant());
        updatedRestaurant.setDescription(restaurantAddRequest.getDescription());
        updatedRestaurant.setFreeDelivery(restaurantAddRequest.getFreeDelivery());
        updatedRestaurant.setAddress(addressService.findAddressById(restaurantAddRequest.getAddressId()));
        ;
        updatedRestaurant.setRestaurantCategory(restaurantCategoryService.findRestaurantCategoryById(restaurantAddRequest.getRestaurantCategoryId()));
        log.info("Update restaurant in RestaurantService with nameOfRestaurant: {}, description{}, " +
                "freeDelivery: {} " + " address: {}", updatedRestaurant.getNameOfRestaurant(), updatedRestaurant.getDescription(), updatedRestaurant.getFreeDelivery(), updatedRestaurant.getAddress());
//        addressRepository.save(restaurantAddRequest.getAddress());
//        restaurantCategoryRepository.save(restaurantAddRequest.getRestaurantCategory());
        restaurantRepository.save(updatedRestaurant);
        return ResponseEntity.ok(new MessageResponse("Restaurant updated successfully!"));
    }

    @Override
    public ResponseEntity<?> deleteRestaurant(Long id) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant"));
        log.info("In deleteRestaurant AdminService - delete restaurant id: '{}'", id);
        restaurantRepository.delete(restaurant);
        return ResponseEntity.ok(new MessageResponse("Restaurant deleted successfully!"));
    }

}
