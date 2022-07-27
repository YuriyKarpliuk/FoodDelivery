package yurii.karpliuk.foodDelivery.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderItemResponse {
    private Integer quantity;

    private Integer totalSum;

    private DishResponse dishResponse;

    private Long orderId;
}
