package com.example.pokeapi_mirror.model.util;

import com.example.pokeapi_mirror.annotation.IntegerPositive;

import jakarta.validation.constraints.Size;

public class PokemonId {
	@IntegerPositive(message = "{id.type}")
	@Size(max = 5, message = "{id.size}")
    private String id;

	public PokemonId(String id) {
		this.id = id;
	}

	public PokemonId() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
