package yurii.karpliuk.foodDelivery.service;

import org.springframework.http.ResponseEntity;
import yurii.karpliuk.foodDelivery.exception.NotFoundException;

public interface CourierService {
    ResponseEntity<?> acceptOrder(Long courierId, Long orderId) throws NotFoundException;
}
