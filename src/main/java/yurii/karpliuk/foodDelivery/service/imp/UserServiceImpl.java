package yurii.karpliuk.foodDelivery.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.dto.request.LoginRequest;
import yurii.karpliuk.foodDelivery.dto.request.SignupRequest;
import yurii.karpliuk.foodDelivery.dto.response.JwtResponse;
import yurii.karpliuk.foodDelivery.dto.response.MessageResponse;
import yurii.karpliuk.foodDelivery.entity.Role;
import yurii.karpliuk.foodDelivery.entity.User;
import yurii.karpliuk.foodDelivery.enums.UserRole;
import yurii.karpliuk.foodDelivery.exception.InvalidCardException;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.repository.RoleRepository;
import yurii.karpliuk.foodDelivery.repository.UserRepository;
import yurii.karpliuk.foodDelivery.security.jwt.JwtUtils;
import yurii.karpliuk.foodDelivery.security.service.UserDetailsImpl;
import yurii.karpliuk.foodDelivery.service.UserService;
import yurii.karpliuk.foodDelivery.validator.CardValidator;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findUserById(Long userId) {
        log.info("Try to find user by id: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException
                        ("User by id " + userId + " was not found."));
    }

    @Override
    public User  findUserByEmail(String email) {
        log.info("Try to find user by email: {}", email);
        return userRepository.findByEmail(email).
                orElseThrow(()-> new NotFoundException
                        ("User by email " + email + " was not found."));
    }

    @Override
    public User createUser(String firstName, String email, String password, String card) throws InvalidCardException {
        User user = new User();
        user.setFirstName(firstName);
        user.setEmail(email);
        user.setPassword(password);
        if (CardValidator.isValid(card)) {
            user.setCard(card);
        } else throw new InvalidCardException();
        Role userRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER)
                .orElseThrow(() -> new NotFoundException("Error: role "));
        user.getRoles().add(userRole);
        log.info("Save user to repository with firstName: {}, email: {}," +
                "card: {}", firstName, email, card);
        return userRepository.save(user);

    }

    @Override
    public ResponseEntity<?> registryUser(SignupRequest signupRequest) throws InvalidCardException {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = createUser(signupRequest.getFirstName(),signupRequest.getEmail(),passwordEncoder.encode(signupRequest.getPassword()),signupRequest.getCard());
        log.info("In registerUser AuthServiceImpl - register user: '{}'", user.getEmail());
        return ResponseEntity.ok(new MessageResponse("User register successfully!"));
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Try to delete user by id: {}", userId);
        userRepository.deleteById(userId);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        log.info("Find all users on page: {}", pageable.getPageNumber());
        return userRepository.findAll(pageable);
    }


    @Override
    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwt);
        jwtResponse.setId(userDetails.getId());
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setEmail(userDetails.getEmail());
        jwtResponse.setRoles(roles);
        log.info("In authenticateUser AuthServiceImpl - authenticate user: '{}'", jwtResponse.getEmail());
        return ResponseEntity.ok(jwtResponse);
    }


    @Override
    public Boolean existByEmail(String email) {
        log.info("Check user by email: {}", email);
        return userRepository.existsByEmail(email);
    }
}
