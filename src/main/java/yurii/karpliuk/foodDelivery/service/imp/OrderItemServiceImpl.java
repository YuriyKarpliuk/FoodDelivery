package yurii.karpliuk.foodDelivery.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.dto.response.DishResponse;
import yurii.karpliuk.foodDelivery.dto.response.MessageResponse;
import yurii.karpliuk.foodDelivery.dto.response.OrderItemResponse;
import yurii.karpliuk.foodDelivery.entity.Dish;
import yurii.karpliuk.foodDelivery.entity.OrderItem;
import yurii.karpliuk.foodDelivery.entity.User;
import yurii.karpliuk.foodDelivery.repository.DishRepository;
import yurii.karpliuk.foodDelivery.repository.OrderItemRepository;
import yurii.karpliuk.foodDelivery.repository.UserRepository;
import yurii.karpliuk.foodDelivery.service.DishService;
import yurii.karpliuk.foodDelivery.service.OrderItemService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DishService dishService;

    @Override
    public Integer totalSumOfOrder(Integer quantity, Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found for this id :: " + dishId));
        return dish.getPrice() * quantity;
    }

    public ResponseEntity<?> createOrderItems(Integer quantity, Long dishId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found for this id :: " + dishId));
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found for this email :: " + userDetails));
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(quantity);
        orderItem.setTotalSum(totalSumOfOrder(quantity, dishId));
        orderItem.setDish(dish);
        orderItem.setUser(user);
        orderItemRepository.save(orderItem);
        log.info("In createOrderItems OrderItemServiceImpl - order item created");
        return ResponseEntity.ok(new MessageResponse("Order item added successfully!"));
    }

    @Override
    public OrderItemResponse buildOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setQuantity(orderItem.getQuantity());
        response.setTotalSum(orderItem.getTotalSum());
        Dish dish = new Dish();
        response.setDishResponse(dishService.buildDishResponse(dish));
        response.setOrderId(orderItem.getOrder().getId());
        return response;
    }
}
