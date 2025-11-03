package com.example.pokeapi_mirror.repository;

import com.example.pokeapi_mirror.model.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer>, JpaSpecificationExecutor<Pokemon> {
	
	boolean existsByName(String name);
    void deleteByName(String name);
}

