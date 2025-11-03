package com.example.pokeapi_mirror.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class OfficialArtWork {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String front_default;

    @Column
    private String front_shiny;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "other_id", referencedColumnName = "id")
    private Other other;

    public OfficialArtWork(String front_default, String front_shiny) {
        this.front_default = front_default;
        this.front_shiny = front_shiny;
    }

    public OfficialArtWork() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public String getFront_shiny() {
        return front_shiny;
    }

    public void setFront_shiny(String front_shiny) {
        this.front_shiny = front_shiny;
    }

    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "OfficialArtWork{" +
                "front_shiny='" + front_shiny + '\'' +
                ", front_default='" + front_default + '\'' +
                ", id=" + id +
                '}';
    }
}
