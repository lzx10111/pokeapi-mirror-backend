package com.example.pokeapi_mirror.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String url;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "abilities_id", referencedColumnName = "id")
    private Abilities abilities;

    public Ability(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Ability() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Abilities getAbilities() {
        return abilities;
    }

    public void setAbilities(Abilities abilities) {
        this.abilities = abilities;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
