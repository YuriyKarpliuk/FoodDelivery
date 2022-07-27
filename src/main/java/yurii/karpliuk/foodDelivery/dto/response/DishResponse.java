package yurii.karpliuk.foodDelivery.dto.response;


import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DishResponse {
    private String dishName;

    private String description;

    private Integer weight;

    private Integer price;

    private List<String> imagesUrl;

    private List<String> dishCategoriesNames;

    private String restaurantName;

}
