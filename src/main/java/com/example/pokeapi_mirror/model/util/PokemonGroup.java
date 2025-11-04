package com.example.pokeapi_mirror.model.util;

public class PokemonGroup {
    private String start;
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
