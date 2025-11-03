package com.example.pokeapi_mirror.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Sprites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @JsonManagedReference
    @OneToOne(mappedBy = "sprites", cascade = CascadeType.ALL)
    private Other other;

    @Column
    private String back_default;

    @Column
    private String back_female;

    @Column
    private String back_shiny;

    @Column
    private String back_shiny_female;

    @Column
    private String front_default;

    @Column
    private String front_female;

    @Column
    private String front_shiny;

    @Column
    private String front_shiny_female;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "pokemon_id", referencedColumnName = "id")
    private Pokemon pokemon;

    public Sprites(Other other, String back_default, String back_female, String back_shiny, String back_shiny_female, String front_default, String front_female, String front_shiny, String front_shiny_female) {
        this.other = other;
        this.back_default = back_default;
        this.back_female = back_female;
        this.back_shiny = back_shiny;
        this.back_shiny_female = back_shiny_female;
        this.front_default = front_default;
        this.front_female = front_female;
        this.front_shiny = front_shiny;
        this.front_shiny_female = front_shiny_female;
    }

    public Sprites() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
        other.setSprites(this);
    }

    public String getBack_default() {
        return back_default;
    }

    public void setBack_default(String back_default) {
        this.back_default = back_default;
    }

    public String getBack_female() {
        return back_female;
    }

    public void setBack_female(String back_female) {
        this.back_female = back_female;
    }

    public String getBack_shiny() {
        return back_shiny;
    }

    public void setBack_shiny(String back_shiny) {
        this.back_shiny = back_shiny;
    }

    public String getBack_shiny_female() {
        return back_shiny_female;
    }

    public void setBack_shiny_female(String back_shiny_female) {
        this.back_shiny_female = back_shiny_female;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public String getFront_female() {
        return front_female;
    }

    public void setFront_female(String front_female) {
        this.front_female = front_female;
    }

    public String getFront_shiny() {
        return front_shiny;
    }

    public void setFront_shiny(String front_shiny) {
        this.front_shiny = front_shiny;
    }

    public String getFront_shiny_female() {
        return front_shiny_female;
    }

    public void setFront_shiny_female(String front_shiny_female) {
        this.front_shiny_female = front_shiny_female;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public String toString() {
        return "Sprites{" +
                "front_shiny_female='" + front_shiny_female + '\'' +
                ", front_shiny='" + front_shiny + '\'' +
                ", front_female='" + front_female + '\'' +
                ", front_default='" + front_default + '\'' +
                ", back_shiny_female='" + back_shiny_female + '\'' +
                ", back_shiny='" + back_shiny + '\'' +
                ", back_female='" + back_female + '\'' +
                ", back_default='" + back_default + '\'' +
                ", other=" + other +
                ", id=" + id +
                '}';
    }
}
