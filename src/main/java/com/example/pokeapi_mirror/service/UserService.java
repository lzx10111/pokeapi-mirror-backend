package com.example.pokeapi_mirror.service;

import com.example.pokeapi_mirror.model.entity.Favorite;
import com.example.pokeapi_mirror.model.util.FavoriteId;
import com.example.pokeapi_mirror.repository.FavoriteRepository;
import com.example.pokeapi_mirror.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.pokeapi_mirror.repository.FavoriteSpecifications.usernameLike;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class UserService {

    private final PokemonRepository pokemonRepository;
    private final FavoriteRepository favoriteRepository;

    public UserService(PokemonRepository pokemonRepository, FavoriteRepository favoriteRepository) {
        this.pokemonRepository = pokemonRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @SuppressWarnings("removal")
	public List<Favorite> findAllFavorites(String username) {
        return favoriteRepository.findAll(where(usernameLike(username)));
    }
    
    public boolean existsPokemonById(Integer pokemonId) {
    	return pokemonRepository.existsById(pokemonId);
    }

    public void saveFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    public boolean favoriteExists(Favorite favorite) {
        return favoriteRepository.existsById(new FavoriteId(favorite.getUsername(), favorite.getPokemonId()));
    }
    
    public boolean isUserFavorite(String username, Integer pokemonId) {
    	return favoriteRepository.existsByUsernameAndPokemonId(username, pokemonId);
    }

    public Integer getTotalFavoritePokemonCount(Integer pokemonId) {
        return favoriteRepository.totalFavoritePokemonCount(pokemonId);
    }
}
