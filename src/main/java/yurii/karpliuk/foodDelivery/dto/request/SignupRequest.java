package yurii.karpliuk.foodDelivery.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull(message = "Email is required")
    @Email(message = "Wrong email")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 8, max = 16)
    private String password;

    private String card;

}
