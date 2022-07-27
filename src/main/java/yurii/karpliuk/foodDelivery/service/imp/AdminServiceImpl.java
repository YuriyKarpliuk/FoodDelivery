package yurii.karpliuk.foodDelivery.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.dto.request.DishAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.DishCategoryAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.RestaurantAddRequest;
import yurii.karpliuk.foodDelivery.dto.response.MessageResponse;
import yurii.karpliuk.foodDelivery.dto.response.UserResponse;
import yurii.karpliuk.foodDelivery.entity.*;
import yurii.karpliuk.foodDelivery.enums.UserRole;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.repository.*;
import yurii.karpliuk.foodDelivery.service.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> addAdmin(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Error: User with email '" + email + "'"));
        user.getRoles().add(roleRepository.findByName(UserRole.ROLE_ADMIN).orElseThrow(() -> new NotFoundException("Error: ROLE_ADMIN")));
        log.info("In addAdmin AdminService - make user: '{}' to admin", user.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User has a new role - Admin!"));
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAllCouriers() throws NotFoundException {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        Role courierRole = roleRepository.findByName(UserRole.ROLE_COURIER).orElseThrow(() -> new NotFoundException("ROLE_COURIER"));
        users.stream().forEach(user -> {
            if (user.getRoles().contains(courierRole)) {
                userResponses.add(buildUserResponse(user));
            }
        });
        log.info("In findAllOwners AdminService - found {} couriers", userResponses.size());
        return ResponseEntity.ok(userResponses);
    }

    @Override
    public ResponseEntity<UserResponse> findById(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
        log.info("In findById AdminService - found user id: '{}'", id);
        return ResponseEntity.ok(buildUserResponse(user));
    }


    @Override
    public ResponseEntity<?> deleteUser(Long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User"));
        log.info("In deleteDish AdminService - delete dish id: '{}'", userId);
        userRepository.delete(user);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    private UserResponse buildUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}
