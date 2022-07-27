package yurii.karpliuk.foodDelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class DishCategory extends IdHolder{


    @ManyToOne
    @JsonIgnore
    private DishCategory parentDishCategory;

    private String name;

    @ManyToMany(mappedBy = "dishCategories")
    private List<Dish> dishes = new ArrayList<>();
}
