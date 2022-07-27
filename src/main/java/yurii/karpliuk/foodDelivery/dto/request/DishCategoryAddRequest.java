package yurii.karpliuk.foodDelivery.dto.request;

import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.foodDelivery.entity.DishCategory;

@Getter
@Setter
public class DishCategoryAddRequest {
    private Long parentDishCategoryId;

    private String name;
}
