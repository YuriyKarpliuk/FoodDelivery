package yurii.karpliuk.foodDelivery.dto.response;

import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.foodDelivery.entity.Address;
import yurii.karpliuk.foodDelivery.entity.OrderItem;
import yurii.karpliuk.foodDelivery.entity.User;
import yurii.karpliuk.foodDelivery.enums.OrderStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private AddressResponse address;

    private Date orderDate;

    private OrderStatus orderStatus;

    private Integer costOfOrder;

    private String userFirstName;

    private List<OrderItemResponse> orderItems;
}
