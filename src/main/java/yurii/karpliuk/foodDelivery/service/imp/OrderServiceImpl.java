package yurii.karpliuk.foodDelivery.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import yurii.karpliuk.foodDelivery.dto.request.AddressAddRequest;
import yurii.karpliuk.foodDelivery.dto.response.*;
import yurii.karpliuk.foodDelivery.entity.*;
import yurii.karpliuk.foodDelivery.enums.OrderStatus;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.exception.OrderIsEmptyException;
import yurii.karpliuk.foodDelivery.repository.DishRepository;
import yurii.karpliuk.foodDelivery.repository.OrderItemRepository;
import yurii.karpliuk.foodDelivery.repository.OrderRepository;
import yurii.karpliuk.foodDelivery.repository.UserRepository;
import yurii.karpliuk.foodDelivery.service.AddressService;
import yurii.karpliuk.foodDelivery.service.OrderItemService;
import yurii.karpliuk.foodDelivery.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressService addressService;

    @Override
    public List<Order> searchOpenOrders() {
        return orderRepository.findByOrderStatus(OrderStatus.CONFIRMED);
    }

    public Integer costOfOrder(List<OrderItem> orderItems) {
        Integer orderCost = 0;
        for (OrderItem orderItem : orderItems) {
            orderCost += orderItem.getTotalSum();
        }
        return orderCost;
    }

    public ResponseEntity<?> createOrder(AddressAddRequest addressAddRequest,Long userId) throws OrderIsEmptyException {
        Order order = new Order();
        Address address = new Address();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User"));
        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrderIdAndUserId(null, userId);
        if (orderItems.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Your order is empty!"));
        } else {
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
            }
            address.setNameOfCity(addressAddRequest.getNameOfCity());
            address.setNameOfCountry(addressAddRequest.getNameOfCountry());
            address.setNameOfStreet(addressAddRequest.getNameOfStreet());
            address.setNumberOfStreet(addressAddRequest.getNumberOfStreet());
            order.setOrderStatus(OrderStatus.CONFIRMED);
            order.setCostOfOrder(costOfOrder(orderItems));
            order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
            order.setUser(user);
            order.setAddress(address);
            order.setOrderItems(orderItems);
            orderRepository.save(order);
            log.info("In createOrder OrderServiceImpl - order created");
            return ResponseEntity.ok(new MessageResponse("Order created successfully!"));
        }
    }

    @Override
    public ResponseEntity<OrderResponse> getInfoAboutOrder(Long orderId) throws NotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order"));
        log.info("In getInfoAboutOrder OrderService - found order with  id: '{}'", orderId);
        return ResponseEntity.ok(buildOrderResponse(order));
    }

    @Override
    public ResponseEntity<?> finishOrder(Long courierId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order"));
        User user = userRepository.findById(courierId).orElseThrow(() -> new NotFoundException("Courier"));
        order.setOrderStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
        List<Order> orders = new ArrayList<>();
        user.setOrders(orders);
        log.info("In finishOrder OrderServiceImpl - order finished");
        return ResponseEntity.ok(new MessageResponse("Order delivered successfully!"));
    }

    @Override
    public ResponseEntity<?> declineOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User"));
        order.setOrderStatus(OrderStatus.DECLINED);
        order.setCourierId(null);
        orderRepository.save(order);
        log.info("In declineOrder OrderServiceImpl - order finished");
        return ResponseEntity.ok(new MessageResponse("Order declined successfully!"));
    }

    private OrderResponse buildOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setAddress(addressService.buildAddressResponse(order.getAddress()));
        List<OrderItemResponse> orderItems = new ArrayList<>();
        List<OrderItem> orderItemList = new ArrayList<>(order.getOrderItems());
        for (int i = 0; i < orderItemList.size(); i++) {
            orderItems.add(orderItemService.buildOrderItemResponse(orderItemList.get(i)));
        }
        response.setOrderItems((orderItems));
        response.setCostOfOrder(order.getCostOfOrder());
        response.setOrderStatus(order.getOrderStatus());
        response.setOrderDate(order.getOrderDate());
        response.setUserFirstName(order.getUser().getFirstName());
        return response;
    }

}
