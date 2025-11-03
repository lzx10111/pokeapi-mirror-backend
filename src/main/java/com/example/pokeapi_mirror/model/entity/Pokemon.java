package com.example.pokeapi_mirror.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pokemon {
    @Id
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer height;

    @Column
    private Integer weight;

    @Column
    private Integer base_experience;

    @JsonManagedReference
    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL)
    private List<Abilities> abilities = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "pokemon", cascade = CascadeType.ALL)
    private Cries cries;

    @JsonManagedReference
    @OneToOne(mappedBy = "pokemon", cascade = CascadeType.ALL)
    private Sprites sprites;

    public Pokemon(String name, Integer id, Integer height, Integer weight, Integer base_experience, List<Abilities> abilities, Cries cries, Sprites sprites) {
        this.name = name;
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.base_experience = base_experience;
        this.abilities = abilities;
        this.cries = cries;
        this.sprites = sprites;
    }

    public Pokemon(Integer id) {
        this.id = id;
    }

    public Pokemon() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
        sprites.setPokemon(this);
    }

    public Cries getCries() {
        return cries;
    }

    public void setCries(Cries cries) {
        this.cries = cries;
        cries.setPokemon(this);
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Abilities> abilities) {
        this.abilities = abilities;
        for (Abilities i : abilities) {
            i.setPokemon(this);
        }
    }

    public Integer getBase_experience() {
        return base_experience;
    }

    public void setBase_experience(Integer base_experience) {
        this.base_experience = base_experience;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean containsId(final List<Favorite> list){
        return list.stream().anyMatch(o -> id.equals(o.getPokemonId()));
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", height=" + height +
                ", weight=" + weight +
                ", base_experience=" + base_experience +
                ", abilities=" + abilities +
                ", cries=" + cries +
                ", sprites=" + sprites +
                '}';
    }
}
