package yurii.karpliuk.foodDelivery.service;

import org.springframework.http.ResponseEntity;
import yurii.karpliuk.foodDelivery.dto.request.DishAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.DishCategoryAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.RestaurantAddRequest;
import yurii.karpliuk.foodDelivery.dto.response.UserResponse;
import yurii.karpliuk.foodDelivery.entity.*;
import yurii.karpliuk.foodDelivery.enums.UserRole;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;

import java.util.List;


public interface AdminService {
    ResponseEntity<List<UserResponse>> findAllCouriers();

    ResponseEntity<UserResponse> findById(Long id) throws NotFoundException;

    ResponseEntity<?> deleteUser(Long userId);

    ResponseEntity<?> addAdmin(String email);
}
