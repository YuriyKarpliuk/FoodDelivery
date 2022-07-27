package yurii.karpliuk.foodDelivery.dto.response;

import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.foodDelivery.entity.Order;
import yurii.karpliuk.foodDelivery.entity.Role;
import yurii.karpliuk.foodDelivery.entity.User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String firstName;

    private String email;

}
