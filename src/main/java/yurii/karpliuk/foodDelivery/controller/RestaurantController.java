package yurii.karpliuk.foodDelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.foodDelivery.dto.request.RestaurantAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.RestaurantSearchRequest;
import yurii.karpliuk.foodDelivery.entity.Restaurant;
import yurii.karpliuk.foodDelivery.entity.RestaurantPhoto;
import yurii.karpliuk.foodDelivery.service.RestaurantPhotoService;
import yurii.karpliuk.foodDelivery.service.RestaurantService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantPhotoService restaurantPhotoService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody RestaurantAddRequest restaurantAddRequest) {
        log.info("In save RestaurantController - restaurant: added");
        return restaurantService.addRestaurant(restaurantAddRequest);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Restaurant getOneById(@PathVariable Long id) {
        return restaurantService.getOneById(id);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole ('ROLE_CUSTOMER') or hasRole ('ROLE_COURIER')")
    @GetMapping("/all")
    public Page<Restaurant> getAll(@RequestParam Integer pageNumber,
                                   @RequestParam Integer pageSize, @RequestParam String sortBy) {
        return restaurantService.findAllRestaurants(pageNumber, pageSize, sortBy);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole ('ROLE_CUSTOMER') or hasRole ('ROLE_COURIER')")
    @GetMapping("/searchByNameLike")
    public ResponseEntity<List<Restaurant>> searchByNameOfRestaurant(@RequestParam String nameOfRestaurant, @RequestParam Integer pageNumber,
                                                     @RequestParam Integer pageSize, @RequestParam String sortBy) {
        return restaurantService.searchByNameOfRestaurant(nameOfRestaurant, pageNumber, pageSize, sortBy);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole ('ROLE_CUSTOMER') or hasRole ('ROLE_COURIER')")
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> search(@RequestBody RestaurantSearchRequest restaurantSearchRequest) {
        return restaurantService.search(restaurantSearchRequest);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRestaurant(@RequestBody RestaurantAddRequest restaurantAddRequest, @PathVariable Long id) {
        log.info("In updateRestaurant RestaurantController - restaurant: updated");
        return restaurantService.updateRestaurant(restaurantAddRequest, id);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        log.info("In deleteRestaurant RestaurantController - restaurant: deleted");
        return restaurantService.deleteRestaurant(id);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @PostMapping("/{id}/add/img")
    public void addImg(@PathVariable(name = "id") Long restaurantId, @RequestParam(required = false) List<MultipartFile> files) {
//        log.info("Request: dishId: {},\n description: {},\n multipartFile: {},\n multipartFiles: {}",
//                userId, description, multipartFile, multipartFiles);
        restaurantPhotoService.saveImgs(files, restaurantId);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN')")
    @DeleteMapping("/{id}/delete/imgs")
    public void deleteImg(@PathVariable Long id) {
        restaurantPhotoService.deleteImgs(id);
    }
}
