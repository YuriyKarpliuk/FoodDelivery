package yurii.karpliuk.foodDelivery.dto.request;

import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.foodDelivery.entity.Address;
import yurii.karpliuk.foodDelivery.entity.RestaurantCategory;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class RestaurantAddRequest {
    private String nameOfRestaurant;
    private String description;
    private Boolean freeDelivery;
    private Long addressId;
    private Long restaurantCategoryId;
}
