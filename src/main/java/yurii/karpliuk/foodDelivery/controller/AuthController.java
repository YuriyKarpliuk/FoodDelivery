package yurii.karpliuk.foodDelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import yurii.karpliuk.foodDelivery.dto.request.LoginRequest;
import yurii.karpliuk.foodDelivery.dto.request.SignupRequest;
import yurii.karpliuk.foodDelivery.dto.response.JwtResponse;
import yurii.karpliuk.foodDelivery.dto.response.MessageResponse;
import yurii.karpliuk.foodDelivery.exception.InvalidCardException;
import yurii.karpliuk.foodDelivery.repository.RoleRepository;
import yurii.karpliuk.foodDelivery.repository.UserRepository;
import yurii.karpliuk.foodDelivery.security.jwt.JwtUtils;
import yurii.karpliuk.foodDelivery.security.service.UserDetailsImpl;
import yurii.karpliuk.foodDelivery.service.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        log.info("In authenticateUser AuthController - user: with email '{}'", loginRequest.getEmail());
        return userService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signUpRequest) throws InvalidCardException {
        log.info("request: {}", signUpRequest);
        return userService.registryUser(signUpRequest);
    }
}
