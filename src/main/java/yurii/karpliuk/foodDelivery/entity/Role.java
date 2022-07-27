package yurii.karpliuk.foodDelivery.entity;

import lombok.Getter;
import lombok.Setter;
import yurii.karpliuk.foodDelivery.enums.UserRole;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="roles")
public class Role extends IdHolder{
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole name;
    @Id
    private Long id;
}
