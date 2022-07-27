package yurii.karpliuk.foodDelivery.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.dto.response.MessageResponse;
import yurii.karpliuk.foodDelivery.dto.response.OrderResponse;
import yurii.karpliuk.foodDelivery.entity.Order;
import yurii.karpliuk.foodDelivery.entity.User;
import yurii.karpliuk.foodDelivery.enums.OrderStatus;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.repository.OrderRepository;
import yurii.karpliuk.foodDelivery.repository.UserRepository;
import yurii.karpliuk.foodDelivery.service.CourierService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> acceptOrder(Long courierId, Long orderId) throws NotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order"));
        User user = userRepository.findById(courierId).orElseThrow(() -> new NotFoundException("User"));
        order.setOrderStatus(OrderStatus.ON_THE_WAY);
        order.setCourierId(courierId);
        orderRepository.save(order);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        user.setOrders(orders);
        userRepository.save(user);
        log.info("Courier with id: {} accept order with orderId: {}", courierId, orderId);
        return ResponseEntity.ok(new MessageResponse("Order accepted!"));
    }

}
