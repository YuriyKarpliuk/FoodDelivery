package yurii.karpliuk.foodDelivery.service;

import org.springframework.http.ResponseEntity;
import yurii.karpliuk.foodDelivery.dto.response.OrderItemResponse;
import yurii.karpliuk.foodDelivery.entity.Dish;
import yurii.karpliuk.foodDelivery.entity.OrderItem;

public interface OrderItemService {

    Integer totalSumOfOrder(Integer quantity, Long dishId);
    ResponseEntity<?> createOrderItems(Integer quantity, Long dishId);

    OrderItemResponse buildOrderItemResponse(OrderItem orderItem);
}
