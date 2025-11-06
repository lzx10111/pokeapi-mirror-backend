package com.example.pokeapi_mirror.model.util;

import com.example.pokeapi_mirror.annotation.IntegerPositive;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PokemonSpecific {
    @NotNull(message = "{id.required}")
    @IntegerPositive(message = "{id.type}")
    @Size(max = 5, message = "{id.size}")
    private String id;

    @NotNull
    @Size(max = 25, message = "{name.size}")
    private String name;
    
    public PokemonSpecific(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public PokemonSpecific() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
