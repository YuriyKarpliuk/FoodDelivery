package yurii.karpliuk.foodDelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yurii.karpliuk.foodDelivery.dto.response.UserResponse;
import yurii.karpliuk.foodDelivery.entity.User;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.service.AdminService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole ('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/make/admin")
    public ResponseEntity<?> makeAdmin(@RequestParam String email) {
        log.info("In makeAdmin AdminController - user with email: '{}' now is admin", email);
        return  adminService.addAdmin(email);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) throws NotFoundException {
        log.info("In findById AdminController - user: found by id: '{}'", id);
        return adminService.findById(id);
    }


    @GetMapping("/getAllCouriers")
    public ResponseEntity<List<UserResponse>> getAllCouriers() throws NotFoundException {
        log.info("In getAllCouriers AdminController");
        return adminService.findAllCouriers();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) throws NotFoundException{
        log.info("In deleteUserById AdminController");
        return adminService.deleteUser(id);
    }

}
