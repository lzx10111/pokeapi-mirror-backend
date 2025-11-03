package com.example.pokeapi_mirror.model.util;

import com.example.pokeapi_mirror.annotation.IntegerPositive;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SearchPokemon {
	@NotNull(message = "{name.required}")
	@Size(max = 25, message = "{name.size}")
    private String name;
	
	@NotNull(message = "{id.required}")
    @IntegerPositive(allowEmptyValue = true, message = "{id.type}")
    @Size(max = 5, message = "{id.size}")
    private String id;
	
    @NotNull(message = "{height.required}")
    @IntegerPositive(allowEmptyValue = true, message = "{height.type}")
    @Size(max = 10, message = "{height.size}")
    private String height_max;
	
    @NotNull(message = "{height.required}")
    @IntegerPositive(allowEmptyValue = true, message = "{height.type}")
    @Size(max = 10, message = "{height.size}")
    private String height_min;
	
    @NotNull(message = "{weight.required}")
    @IntegerPositive(allowEmptyValue = true, message = "{weight.type}")
    @Size(max = 10, message = "{weight.size}")
    private String weight_max;
	
    @NotNull(message = "{weight.required}")
    @IntegerPositive(allowEmptyValue = true, message = "{weight.type}")
    @Size(max = 10, message = "{weight.size}")
    private String weight_min;

	public SearchPokemon() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeight_max() {
        return height_max;
    }

    public void setHeight_max(String height_max) {
        this.height_max = height_max;
    }

    public String getHeight_min() {
        return height_min;
    }

    public void setHeight_min(String height_min) {
        this.height_min = height_min;
    }

    public String getWeight_max() {
        return weight_max;
    }

    public void setWeight_max(String weight_max) {
        this.weight_max = weight_max;
    }

    public String getWeight_min() {
        return weight_min;
    }

    public void setWeight_min(String weight_min) {
        this.weight_min = weight_min;
    }

    
}
