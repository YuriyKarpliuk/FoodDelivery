package yurii.karpliuk.foodDelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yurii.karpliuk.foodDelivery.service.OrderItemService;

@Slf4j
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestParam Integer quantity, @RequestParam Long dishId) {
        log.info("In addItem OrderController - order item dish id:'{}' and quantity :'{}' added", dishId, quantity);
        return orderItemService.createOrderItems(quantity, dishId);
    }
}
