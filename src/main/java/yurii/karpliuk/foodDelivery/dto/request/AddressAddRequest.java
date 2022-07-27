package yurii.karpliuk.foodDelivery.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.SecondaryTable;

@Getter
@Setter
public class AddressAddRequest {
    private String nameOfCountry;

    private String nameOfCity;

    private String nameOfStreet;

    private String numberOfStreet;
}
