package com.example.pokeapi_mirror.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
public class Cries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String latest;

    @Column
    private String legacy;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "pokemon_id", referencedColumnName = "id")
    private Pokemon pokemon;

    public Cries(String latest, String legacy) {
        this.latest = latest;
        this.legacy = legacy;
    }

    public Cries() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getLegacy() {
        return legacy;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public String toString() {
        return "Cries{" +
                "id=" + id +
                ", latest='" + latest + '\'' +
                ", legacy='" + legacy + '\'' +
                '}';
    }
}
