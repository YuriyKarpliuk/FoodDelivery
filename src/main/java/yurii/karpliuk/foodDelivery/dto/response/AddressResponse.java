package yurii.karpliuk.foodDelivery.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {
    private String nameOfCountry;

    private String nameOfCity;

    private String nameOfStreet;

    private String numberOfStreet;
}
