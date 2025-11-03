package com.example.pokeapi_mirror.repository;

import com.example.pokeapi_mirror.model.entity.Favorite;
import com.example.pokeapi_mirror.model.util.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId>, JpaSpecificationExecutor<Favorite> {
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.pokemonId=?1")
    int totalFavoritePokemonCount(Integer pokemonId);
    boolean existsByUsernameAndPokemonId(String username, Integer pokemonId);
}
