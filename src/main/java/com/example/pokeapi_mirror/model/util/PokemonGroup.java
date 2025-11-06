package com.example.pokeapi_mirror.model.util;

import com.example.pokeapi_mirror.annotation.IntegerPositive;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PokemonGroup {
    @NotNull(message = "{id.required}")
    @IntegerPositive(message = "{id.type}")
    @Size(max = 5, message = "{id.size}")
    private String start;

    @NotNull(message = "{id.required}")
    @IntegerPositive(message = "{id.type}")
    @Size(max = 5, message = "{id.size}")
    private String end;
    
    public PokemonGroup(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public PokemonGroup() {
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
