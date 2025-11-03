package com.example.pokeapi_mirror.model.util;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
    private Integer pokemonId;

    public FavoriteId(String username, Integer pokemonId) {
        this.username = username;
        this.pokemonId = pokemonId;
    }

    public FavoriteId() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(username, that.username) && Objects.equals(pokemonId, that.pokemonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, pokemonId);
    }
}
