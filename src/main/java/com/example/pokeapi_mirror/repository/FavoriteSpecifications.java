package com.example.pokeapi_mirror.repository;

import com.example.pokeapi_mirror.model.entity.Favorite;
import org.springframework.data.jpa.domain.Specification;

public class FavoriteSpecifications {
    public static Specification<Favorite> usernameLike(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("username"), username);
        };
    }
}
