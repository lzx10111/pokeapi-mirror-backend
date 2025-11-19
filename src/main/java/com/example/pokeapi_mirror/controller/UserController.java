package com.example.pokeapi_mirror.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pokeapi_mirror.annotation.IntegerPositive;
import com.example.pokeapi_mirror.model.entity.Favorite;
import com.example.pokeapi_mirror.model.util.PokemonId;
import com.example.pokeapi_mirror.model.util.SimpleMessage;
import com.example.pokeapi_mirror.service.UserService;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
	UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/add_favorite")
	public ResponseEntity<?> addFavorite(@RequestBody @Valid PokemonId pokemonId, @AuthenticationPrincipal Jwt jwt) {
		if (!userService.existsPokemonById(Integer.parseInt(pokemonId.getId()))) {
			return new ResponseEntity<>(new SimpleMessage("pokemonId", pokemonId.getId().toString(), "Pokemon no encontrado."), HttpStatus.NOT_FOUND);
		}
		
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		Favorite favorite = new Favorite(jwt.getSubject(), Integer.parseInt(pokemonId.getId()));
		
		if (userService.favoriteExists(favorite)) {
            userService.deleteFavorite(favorite);
			userService.favoriteCountTotalDecrease(Integer.parseInt(pokemonId.getId()));
            map.put("isFavorite", false);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            userService.saveFavorite(favorite);
            userService.favoriteCountTotalIncrease(Integer.parseInt(pokemonId.getId()));
			map.put("isFavorite", true);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/find_favorites")
	public ResponseEntity<?> findFavorites(@AuthenticationPrincipal Jwt jwt) {
		List<Favorite> fav = userService.findAllFavorites(jwt.getSubject());

		return new ResponseEntity<>(fav, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/is_user_favorite/{id}")
    public ResponseEntity<?> isUserFavorite(@PathVariable 
    	@NotNull(message = "{id.required}")
		@IntegerPositive(message = "{id.type}")
		@Size(max = 5, message = "{id.size}") String id, @AuthenticationPrincipal Jwt jwt) {
 
        HashMap<String, Boolean> msg = new HashMap<String, Boolean>();
		msg.put("isFavorite", userService.isUserFavorite(jwt.getSubject(), Integer.parseInt(id)));
		
		return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
