package com.example.pokeapi_mirror.model.entity;

import com.example.pokeapi_mirror.model.util.FavoriteId;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites")
@IdClass(FavoriteId.class)
public class Favorite {
	@Id
    private String username;
    @Id
    private Integer pokemonId;

	public Favorite(String username, Integer pokemonId) {
		this.username = username;
		this.pokemonId = pokemonId;
	}

	public Favorite() {
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPokemonId() {
		return pokemonId;
	}

	public void setPokemonId(Integer pokemonId) {
		this.pokemonId = pokemonId;
	}
}
