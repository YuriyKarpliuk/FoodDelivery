package yurii.karpliuk.foodDelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Dish extends IdHolder {

    private String dishName;

    private String description;

    private Integer weight;

    private Integer price;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "dish_to_dishCategory", joinColumns = @JoinColumn(name = "dish_id"), inverseJoinColumns = @JoinColumn(name = "dishCategory_id"))
    private List<DishCategory> dishCategories;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderItem> orderItems = new HashSet<>();

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DishPhoto> photos;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JsonIgnore
    private Restaurant restaurant;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(dishName, dish.dishName) && Objects.equals(description, dish.description) && Objects.equals(weight, dish.weight) && Objects.equals(price, dish.price) && Objects.equals(dishCategories, dish.dishCategories) && Objects.equals(orderItems, dish.orderItems) && Objects.equals(photos, dish.photos) && Objects.equals(restaurant, dish.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishName, description, weight, price, dishCategories, orderItems, photos, restaurant);
    }

    @Override
    public String toString() {
        return "Dish{" + "dishName='" + dishName + '\'' + ", description='" + description + '\'' + ", weight=" + weight + ", price=" + price + ", dishCategories=" + dishCategories + ", orderItems=" + orderItems + ", photos=" + photos + ", restaurant=" + restaurant + '}';
    }
}
