package yurii.karpliuk.foodDelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.foodDelivery.enums.OrderStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="orders")
public class Order extends IdHolder{

    private OrderStatus orderStatus;

    private Integer costOfOrder;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private Date orderDate;

    private Long courierId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderStatus == order.orderStatus && Objects.equals(costOfOrder, order.costOfOrder) && Objects.equals(address, order.address) && Objects.equals(orderDate, order.orderDate) && Objects.equals(orderItems, order.orderItems) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStatus, costOfOrder, address, orderDate, orderItems, user);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderStatus=" + orderStatus +
                ", costOfOrder=" + costOfOrder +
                ", address=" + address +
                ", orderDate=" + orderDate +
                ", orderItems=" + orderItems +
                ", user=" + user +
                '}';
    }
}
