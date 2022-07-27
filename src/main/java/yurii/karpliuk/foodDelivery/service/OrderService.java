package yurii.karpliuk.foodDelivery.service;

import org.springframework.http.ResponseEntity;
import yurii.karpliuk.foodDelivery.dto.request.AddressAddRequest;
import yurii.karpliuk.foodDelivery.dto.request.SignupRequest;
import yurii.karpliuk.foodDelivery.dto.response.OrderResponse;
import yurii.karpliuk.foodDelivery.entity.Order;
import yurii.karpliuk.foodDelivery.entity.OrderItem;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;
import yurii.karpliuk.foodDelivery.exception.OrderIsEmptyException;

import java.util.List;

public interface OrderService {
    List<Order> searchOpenOrders();

    Integer costOfOrder(List<OrderItem> orderItems);
     ResponseEntity<?> createOrder(AddressAddRequest addressAddRequest,Long userId)throws OrderIsEmptyException;
    public ResponseEntity<OrderResponse> getInfoAboutOrder(Long orderId)throws NotFoundException;

    ResponseEntity<?> finishOrder(Long courierId, Long orderId);

    ResponseEntity<?> declineOrder(Long userId, Long orderId);
}
