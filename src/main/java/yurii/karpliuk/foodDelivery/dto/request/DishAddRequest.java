package yurii.karpliuk.foodDelivery.dto.request;

import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.foodDelivery.entity.DishCategory;

import javax.persistence.ManyToOne;

@Getter
@Setter
public class DishAddRequest {
    private String dishName;

    private String description;

    private Integer weight;

    private Integer price;

    private Long restaurantId;

    private Long dishCategoryId;
}
