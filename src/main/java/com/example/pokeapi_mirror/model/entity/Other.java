package com.example.pokeapi_mirror.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Other {

//    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @JsonManagedReference
    @JsonProperty("official-artwork")
    @OneToOne(mappedBy = "other", cascade = CascadeType.ALL)
    private OfficialArtWork official_artwork;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "sprites_id", referencedColumnName = "id")
    private Sprites sprites;

    public Other(OfficialArtWork official_artwork) {
        this.official_artwork = official_artwork;
    }

    public Other() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OfficialArtWork getOfficial_artwork() {
        return official_artwork;
    }

    public void setOfficial_artwork(OfficialArtWork official_artwork) {
        this.official_artwork = official_artwork;
        official_artwork.setOther(this);
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    @Override
    public String toString() {
        return "Other{" +
                "id=" + id +
                ", official_artwork=" + official_artwork +
                '}';
    }
}
