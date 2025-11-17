package com.example.pokeapi_mirror.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorites_count")
public class FavoriteCount {
    @Id
    @Column
    private Integer pokemon_id;

    @Column
    private Integer total;

    public FavoriteCount(Integer pokemon_id, Integer total) {
        this.pokemon_id = pokemon_id;
        this.total = total;
    }

    public FavoriteCount() {
    }

    public Integer getPokemon_id() {
        return pokemon_id;
    }

    public void setPokemon_id(Integer pokemon_id) {
        this.pokemon_id = pokemon_id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
