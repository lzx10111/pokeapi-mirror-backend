package com.example.pokeapi_mirror.repository;

import com.example.pokeapi_mirror.model.entity.Favorite;
import com.example.pokeapi_mirror.model.util.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId>, JpaSpecificationExecutor<Favorite> {
    long countByPokemonId(Integer pokemonId);
    boolean existsByUsernameAndPokemonId(String username, Integer pokemonId);
}
