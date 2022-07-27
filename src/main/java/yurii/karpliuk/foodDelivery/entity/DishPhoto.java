package yurii.karpliuk.foodDelivery.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class DishPhoto extends IdHolder{


    private  String  imgUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    private Dish dish;
}
