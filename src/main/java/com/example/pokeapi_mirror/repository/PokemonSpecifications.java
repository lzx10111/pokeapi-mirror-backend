package com.example.pokeapi_mirror.repository;

import com.example.pokeapi_mirror.model.entity.Pokemon;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class PokemonSpecifications {

    public static Specification<Pokemon> nameLike(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    public static Specification<Pokemon> idLike(String id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null || id.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("id"), Integer.parseInt(id));
        };
    }

    public static Specification<Pokemon> heightLike(String min, String max) {
        return (root, query, criteriaBuilder) -> {
            Predicate minPred;
            Predicate maxPred;
            String value1 = min;
            String value2 = max;

            if (min == null || min.isEmpty()) {
                value1 = "0";
            }

            if (max == null || max.isEmpty()) {
                value2 = "999999999";
            }

            minPred = criteriaBuilder.greaterThanOrEqualTo(root.get("height"), Integer.parseInt(value1));
            maxPred = criteriaBuilder.lessThanOrEqualTo(root.get("height"), Integer.parseInt(value2));

            return criteriaBuilder.and(minPred, maxPred);
        };
    }

    public static Specification<Pokemon> weightLike(String min, String max) {
        return (root, query, criteriaBuilder) -> {
            Predicate minPred;
            Predicate maxPred;
            String value1 = min;
            String value2 = max;

            if (min == null || min.isEmpty()) {
                value1 = "0";
            }

            if (max == null || max.isEmpty()) {
                value2 = "999999999";
            }

            minPred = criteriaBuilder.greaterThanOrEqualTo(root.get("weight"), Integer.parseInt(value1));
            maxPred = criteriaBuilder.lessThanOrEqualTo(root.get("weight"), Integer.parseInt(value2));

            return criteriaBuilder.and(minPred, maxPred);
        };
    }

    public static Specification<Pokemon> idGreaterThanOrEqualTo(String id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null || id.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("id"), Integer.parseInt(id));
        };
    }

    public static Specification<Pokemon> idLessThanOrEqualTo(String id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null || id.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.lessThanOrEqualTo(root.get("id"), Integer.parseInt(id));
        };
    }
}
