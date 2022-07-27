package yurii.karpliuk.foodDelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yurii.karpliuk.foodDelivery.dto.request.AddressAddRequest;
import yurii.karpliuk.foodDelivery.dto.response.OrderResponse;
import yurii.karpliuk.foodDelivery.entity.Order;
import yurii.karpliuk.foodDelivery.exception.OrderIsEmptyException;
import yurii.karpliuk.foodDelivery.service.CourierService;
import yurii.karpliuk.foodDelivery.service.OrderService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CourierService courierService;

    @PreAuthorize("hasRole ('ROLE_CUSTOMER')")
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody AddressAddRequest addressAddRequest, Long userId) throws OrderIsEmptyException {
        return orderService.createOrder(addressAddRequest, userId);
    }

    @PreAuthorize("hasRole('ROLE_COURIER')")
    @PostMapping("/finish")
    public ResponseEntity<?> finishOrder(@RequestParam Long courierId, @RequestParam Long orderId) {
        log.info("In finishOrder OrderController - order with id:'{}' and courier id:'{}' finished", orderId, courierId);
        return orderService.finishOrder(courierId, orderId);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/decline")
    public ResponseEntity<?> declineOrder(@RequestParam Long userId, @RequestParam Long orderId) {
        log.info("In declineOrder OrderController - order with id:'{}' and user id:'{}' declined", orderId, userId);
        return orderService.declineOrder(userId, orderId);
    }

    @PreAuthorize("hasRole('ROLE_COURIER')")
    @GetMapping("/openOrders")
    public List<Order> openOrders() {
        return orderService.searchOpenOrders();
    }

    @PreAuthorize("hasRole('ROLE_COURIER')")
    @PostMapping("/accept")
    public ResponseEntity<?> acceptOrder(@RequestParam Long courierId, @RequestParam Long orderId) {
        log.info("In acceptOrder OrderController - order with id:'{}' and courier id:'{}' accepted", orderId, courierId);
        return courierService.acceptOrder(courierId, orderId);
    }

    @PreAuthorize("hasRole('ROLE_COURIER') or hasRole('ROLE_CUSTOMER')")
    @GetMapping("/getInfo")
    public ResponseEntity<OrderResponse> getOrderInfo(@RequestParam Long orderId) {
        return orderService.getInfoAboutOrder(orderId);
    }

}
