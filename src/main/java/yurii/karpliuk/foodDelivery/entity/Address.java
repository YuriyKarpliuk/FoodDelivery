package yurii.karpliuk.foodDelivery.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
public class Address extends IdHolder{


    private String nameOfCountry;

    private String nameOfCity;

    private String nameOfStreet;

    private String numberOfStreet;

    @OneToOne(mappedBy = "address")
    private Restaurant restaurant;

    @OneToOne(mappedBy = "address")
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(nameOfCountry, address.nameOfCountry) && Objects.equals(nameOfCity, address.nameOfCity) && Objects.equals(nameOfStreet, address.nameOfStreet) && Objects.equals(numberOfStreet, address.numberOfStreet) && Objects.equals(restaurant, address.restaurant) && Objects.equals(order, address.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfCountry, nameOfCity, nameOfStreet, numberOfStreet, restaurant, order);
    }

    @Override
    public String toString() {
        return "Address{" +
                "nameOfCountry='" + nameOfCountry  +
                ", nameOfCity='" + nameOfCity  +
                ", nameOfStreet='" + nameOfStreet  +
                ", numberOfStreet='" + numberOfStreet  +
                '}';
    }
}
