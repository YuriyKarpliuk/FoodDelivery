package yurii.karpliuk.foodDelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class OrderItem extends IdHolder {

    private Integer quantity;

    private Integer totalSum;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Dish dish;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;


    @Override
    public String toString() {
        return "OrderItem{" +
                "quantity=" + quantity +
                ", totalSum=" + totalSum +
                ", dish=" + dish +
                ", order=" + order +
                ", user=" + user +
                '}';
    }
}
