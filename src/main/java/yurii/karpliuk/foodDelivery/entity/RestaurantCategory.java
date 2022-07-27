package yurii.karpliuk.foodDelivery.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class RestaurantCategory extends IdHolder {


    private String categoryName;

    @OneToMany(mappedBy = "restaurantCategory", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants = new ArrayList<>();
}
