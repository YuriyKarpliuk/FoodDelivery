package yurii.karpliuk.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yurii.karpliuk.foodDelivery.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
