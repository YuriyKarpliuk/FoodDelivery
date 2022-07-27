package yurii.karpliuk.foodDelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yurii.karpliuk.foodDelivery.dto.request.DishAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.DishSearchRequest;
import yurii.karpliuk.foodDelivery.dto.response.DishResponse;
import yurii.karpliuk.foodDelivery.entity.Dish;
import yurii.karpliuk.foodDelivery.service.DishPhotoService;
import yurii.karpliuk.foodDelivery.service.DishService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishPhotoService dishPhotoService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody DishAddRequest dishAddRequest) {
        log.info("In save DishController - dish: added");
        return dishService.addDish(dishAddRequest);
    }

    @PreAuthorize("hasRole ('ROLE_ADMIN') or hasRole ('ROLE_CUSTOMER') or hasRole ('ROLE_COURIER')")
    @GetMapping("/search")
    public ResponseEntity<List<DishResponse>> search(@RequestBody DishSearchRequest dishSearchRequest) {
        return dishService.search(dishSearchRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DishResponse> getOneById(@PathVariable Long id) {
        return dishService.getOneById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDish(@RequestBody DishAddRequest dishAddRequest, @PathVariable Long id) {
        log.info("In update DishController - dish: updated");
        return dishService.updateDish(dishAddRequest, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable Long id) {
        log.info("In delete DishController - dish: deleted");
        return dishService.deleteDish(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}/add/img")
    public void addImg(@PathVariable(name = "id") Long dishId, @RequestParam(required = false) List<MultipartFile> files) {
//        log.info("Request: dishId: {},\n description: {},\n multipartFile: {},\n multipartFiles: {}",
//                userId, description, multipartFile, multipartFiles);
        dishPhotoService.saveImgs(files, dishId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/delete/imgs")
    public void deleteImg(@PathVariable Long id) {
        dishPhotoService.deleteImgs(id);
    }
}
