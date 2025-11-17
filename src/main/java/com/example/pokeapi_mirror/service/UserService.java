package com.example.pokeapi_mirror.service;

import com.example.pokeapi_mirror.model.entity.Favorite;
import com.example.pokeapi_mirror.model.entity.FavoriteCount;
import com.example.pokeapi_mirror.model.util.FavoriteId;
import com.example.pokeapi_mirror.repository.FavoriteCountRepository;
import com.example.pokeapi_mirror.repository.FavoriteRepository;
import com.example.pokeapi_mirror.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.pokeapi_mirror.repository.FavoriteSpecifications.usernameLike;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class UserService {

    private final PokemonRepository pokemonRepository;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteCountRepository favoriteCountRepository;

    public UserService(PokemonRepository pokemonRepository, FavoriteRepository favoriteRepository,
            FavoriteCountRepository favoriteCountRepository) {
        this.pokemonRepository = pokemonRepository;
        this.favoriteRepository = favoriteRepository;
        this.favoriteCountRepository = favoriteCountRepository;
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

    public void favoriteCountTotalIncrease(Integer id) {
        Optional<FavoriteCount> opt = getFavoriteCountByID(id);
        FavoriteCount favoriteCount;

        if (opt.isPresent()) {
            favoriteCount = opt.get();
            favoriteCount.setTotal(favoriteCount.getTotal() + 1);
        }
        else {
            favoriteCount = new FavoriteCount(id, 1);
        }

        favoriteCountRepository.save(favoriteCount);
    }

    public void favoriteCountTotalDecrease(Integer id) {
        Optional<FavoriteCount> opt = getFavoriteCountByID(id);
        FavoriteCount favoriteCount = opt.get();

        if (favoriteCount.getTotal() > 1) {
            favoriteCount.setTotal(favoriteCount.getTotal() - 1);
            favoriteCountRepository.save(favoriteCount);
        }
        else {
            favoriteCountRepository.delete(favoriteCount);
        }
    }

    public Optional<FavoriteCount> getFavoriteCountByID(Integer id) {
        return favoriteCountRepository.findById(id);
    }
}
