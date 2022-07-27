package yurii.karpliuk.foodDelivery.repository.spec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import yurii.karpliuk.foodDelivery.dto.request.RestaurantSearchRequest;
import yurii.karpliuk.foodDelivery.entity.Restaurant;
import yurii.karpliuk.foodDelivery.entity.RestaurantCategory;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantSpecification implements Specification<Restaurant> {

    private RestaurantSearchRequest restaurantSearchRequest;

    public RestaurantSpecification(RestaurantSearchRequest restaurantSearchRequest) {
        this.restaurantSearchRequest = restaurantSearchRequest;
    }

    private void predicatedByName(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!StringUtils.isBlank(restaurantSearchRequest.getNameOfRestaurant())) {
            predicates.add(criteriaBuilder.equal(root.get("nameOfRestaurant"), restaurantSearchRequest.getNameOfRestaurant()));
        }
    }

    private void predicatedByRating(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (restaurantSearchRequest.getRating() != null) {
            if (!StringUtils.isBlank(restaurantSearchRequest.getRatingOperation())) {
                if (restaurantSearchRequest.getRatingOperation().equals("greater")) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("rating"), restaurantSearchRequest.getRating()));
                    return;
                } else if (restaurantSearchRequest.getRatingOperation().equals("less")) {
                    predicates.add(criteriaBuilder.lessThan(root.get("rating"), restaurantSearchRequest.getRating()));
                    return;
                }
            }
            predicates.add(criteriaBuilder.equal(root.get("rating"), restaurantSearchRequest.getRating()));
        }
    }

    private void predicateByRestaurantCategory(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!StringUtils.isBlank(restaurantSearchRequest.getCategoryName())) {
            Join<Restaurant, RestaurantCategory> restaurantCategoryJoin = root.join("restaurantCategory");
            predicates.add(criteriaBuilder.equal(restaurantCategoryJoin.<String>get("categoryName"), restaurantSearchRequest.getCategoryName()));
        }
    }

    private void predicateByDelivery(Root<Restaurant> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (restaurantSearchRequest.getFreeDelivery() != null) {
            predicates.add(criteriaBuilder.equal(root.get("freeDelivery"), restaurantSearchRequest.getFreeDelivery()));
        }
    }

    @Override
    public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicatedByName(root, criteriaBuilder, predicates);
        predicatedByRating(root, criteriaBuilder, predicates);
        predicateByRestaurantCategory(root, criteriaBuilder, predicates);
        predicateByDelivery(root, criteriaBuilder, predicates);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
