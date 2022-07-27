package yurii.karpliuk.foodDelivery.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import yurii.karpliuk.foodDelivery.dto.request.LoginRequest;
import yurii.karpliuk.foodDelivery.dto.request.SignupRequest;
import yurii.karpliuk.foodDelivery.dto.response.JwtResponse;
import yurii.karpliuk.foodDelivery.dto.response.UserResponse;
import yurii.karpliuk.foodDelivery.entity.User;
import yurii.karpliuk.foodDelivery.enums.UserRole;
import yurii.karpliuk.foodDelivery.exception.InvalidCardException;

import java.util.List;


public interface UserService {
    User findUserById(Long userId);

    User findUserByEmail(String email);

    User createUser(String firstName, String email, String password, String card) throws InvalidCardException;

     ResponseEntity<?> registryUser(SignupRequest signupRequest) throws InvalidCardException;

    void deleteUser(Long userId);

    Page<User> findAllUsers(Pageable pageable);

    ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest);

    Boolean existByEmail(String email);
}
