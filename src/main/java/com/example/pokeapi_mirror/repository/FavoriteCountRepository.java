package com.example.pokeapi_mirror.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pokeapi_mirror.model.entity.FavoriteCount;

@Repository
public interface FavoriteCountRepository extends JpaRepository<FavoriteCount, Integer> {

}
