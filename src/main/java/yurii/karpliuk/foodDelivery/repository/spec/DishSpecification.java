package yurii.karpliuk.foodDelivery.repository.spec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import yurii.karpliuk.foodDelivery.dto.request.DishSearchRequest;
import yurii.karpliuk.foodDelivery.entity.Dish;
import yurii.karpliuk.foodDelivery.entity.DishCategory;
import yurii.karpliuk.foodDelivery.entity.Restaurant;
import yurii.karpliuk.foodDelivery.entity.RestaurantCategory;
import yurii.karpliuk.foodDelivery.repository.DishRepository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class DishSpecification implements Specification<Dish> {
    private DishSearchRequest dishSearchRequest;

    public DishSpecification(DishSearchRequest dishSearchRequest) {
        this.dishSearchRequest = dishSearchRequest;
    }

    private void predicatedByName(Root<Dish> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!StringUtils.isBlank(dishSearchRequest.getDishName())) {
            predicates.add(criteriaBuilder.equal(root.get("dishName"), dishSearchRequest.getDishName()));
        }
    }

    private void predicatedByWeight(Root<Dish> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (dishSearchRequest.getWeight() != null) {
            if (!StringUtils.isBlank(dishSearchRequest.getWeightOperation())) {
                if (dishSearchRequest.getWeightOperation().equals("greater")) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("weight"), dishSearchRequest.getWeight()));
                    return;
                } else if (dishSearchRequest.getWeightOperation().equals("less")) {
                    predicates.add(criteriaBuilder.lessThan(root.get("weight"), dishSearchRequest.getWeight()));
                    return;
                }
            }
            predicates.add(criteriaBuilder.equal(root.get("weight"), dishSearchRequest.getWeight()));
        }
    }

    private void predicatedByPrice(Root<Dish> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (dishSearchRequest.getPrice() != null) {
            if (!StringUtils.isBlank(dishSearchRequest.getPriceOperation())) {
                if (dishSearchRequest.getPriceOperation().equals("greater")) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("price"), dishSearchRequest.getPrice()));
                    return;
                } else if (dishSearchRequest.getPriceOperation().equals("less")) {
                    predicates.add(criteriaBuilder.lessThan(root.get("price"), dishSearchRequest.getPrice()));
                    return;
                }
            }
            predicates.add(criteriaBuilder.equal(root.get("weight"), dishSearchRequest.getPrice()));
        }
    }

    private void predicateByDishCategory(Root<Dish> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!StringUtils.isBlank(dishSearchRequest.getDishName())) {
            Join<Dish, DishCategory> dishDishCategoryJoin = root.join("dishCategory");
            predicates.add(criteriaBuilder.equal(dishDishCategoryJoin.<String>get("name"), dishSearchRequest.getDishName()));
        }
    }

    @Override
    public Predicate toPredicate(Root<Dish> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicatedByName(root, criteriaBuilder, predicates);
        predicatedByWeight(root, criteriaBuilder, predicates);
        predicatedByPrice(root, criteriaBuilder, predicates);
        predicateByDishCategory(root, criteriaBuilder, predicates);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
