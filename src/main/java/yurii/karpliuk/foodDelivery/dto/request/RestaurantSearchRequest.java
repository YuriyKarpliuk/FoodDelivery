package yurii.karpliuk.foodDelivery.dto.request;

import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.foodDelivery.entity.RestaurantCategory;

import javax.persistence.SecondaryTable;
import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
public class RestaurantSearchRequest {
    private String nameOfRestaurant;
    private Integer rating;
    private Boolean freeDelivery;
    private String categoryName;
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String ratingOperation;

}
