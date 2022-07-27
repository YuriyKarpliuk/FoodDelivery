package yurii.karpliuk.foodDelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Restaurant extends IdHolder{

    private String nameOfRestaurant;

    private Integer rating;

    private String description;

    private Boolean freeDelivery;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private RestaurantCategory restaurantCategory;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    List<RestaurantPhoto> restaurantPhotoList;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dish> dish;
}
