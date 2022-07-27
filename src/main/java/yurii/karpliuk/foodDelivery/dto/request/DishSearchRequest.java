package yurii.karpliuk.foodDelivery.dto.request;

import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.foodDelivery.entity.DishCategory;

@Getter
@Setter
public class DishSearchRequest {
    private String dishName;

    private Integer weight;

    private Integer price;
    private String name;
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String weightOperation;
    private String priceOperation;
}
