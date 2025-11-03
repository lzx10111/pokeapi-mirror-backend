package com.example.pokeapi_mirror.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pokeapi_mirror.model.util.PokemonGroup;
import com.example.pokeapi_mirror.model.util.PokemonSpecific;
import com.example.pokeapi_mirror.model.util.SimpleMessage;
import com.example.pokeapi_mirror.service.PokemonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	PokemonService pokemonService;
	
	public AdminController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add_group")
    public ResponseEntity<?> addGroup(@RequestBody @Valid PokemonGroup addGroup) {
		List<SimpleMessage> errors = pokemonService.addGroupHasErrors(addGroup.getStart(),
				addGroup.getEnd(), "addGroupStart",
    			"addGroupEnd");
    	
    	if (!errors.isEmpty()) {
        	return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }
		
        HashMap<String, Integer> msg = new HashMap<String, Integer>();
		Integer total = pokemonService.saveListPokemon(addGroup.getStart(), addGroup.getEnd());
		msg.put("total", total);
		
		return new ResponseEntity<>(msg, HttpStatus.OK);
    }

	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete_group")
    public ResponseEntity<?> deleteGroup(@RequestBody @Valid PokemonGroup deleteGroup) {
    	List<SimpleMessage> errors = pokemonService.deleteGroupHasErrors(deleteGroup.getStart(),
    			deleteGroup.getEnd(), "deleteGroupStart",
    			"deleteGroupEnd");
    	
    	if (!errors.isEmpty()) {
        	return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }

    	HashMap<String, Integer> msg = new HashMap<String, Integer>();
		Integer total = pokemonService.deleteListPokemon(deleteGroup.getStart(), deleteGroup.getEnd());
    	msg.put("total", total);
    	
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add_specific")
    public ResponseEntity<?> addSpecific(@RequestBody @Valid PokemonSpecific addSpecific) {
    	List<SimpleMessage> errors = pokemonService.addSpecificHasErrors(addSpecific.getId(),
    			addSpecific.getName(), "addSpecificId",
    			"addSpecificName");
    	
    	if (!errors.isEmpty()) {
        	return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }
    	
        if (!addSpecific.getId().isEmpty()) {
            pokemonService.savePokemonWithIdPokeAPI(addSpecific.getId());
        }
        else if (!addSpecific.getName().isEmpty()) {
            pokemonService.savePokemonWithNamePokeAPI(addSpecific.getName());
        }
        
        HashMap<String, Integer> msg = new HashMap<String, Integer>();
        msg.put("total", 1);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete_specific")
    public ResponseEntity<?> deleteSpecific(@RequestBody @Valid PokemonSpecific deleteSpecific) {
    	List<SimpleMessage> errors = pokemonService.deleteSpecificHasErrors(deleteSpecific.getId(),
    			deleteSpecific.getName(), "deleteSpecificId",
    			"deleteSpecificName");
    	
    	if (!errors.isEmpty()) {
        	return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }
    	
    	if (!(deleteSpecific.getId().isEmpty() && deleteSpecific.getName().isEmpty())) {
    		pokemonService.deletePokemonWithId(deleteSpecific.getId());
    	}
    	
    	if (!(deleteSpecific.getId().isEmpty()) && deleteSpecific.getName().isEmpty()) {
    		pokemonService.deletePokemonWithId(deleteSpecific.getId());
    	}
    	
    	if (deleteSpecific.getId().isEmpty() && !(deleteSpecific.getName().isEmpty())) {
    		pokemonService.deletePokemonWithName(deleteSpecific.getName());
    	}
    	
    	HashMap<String, Integer> msg = new HashMap<String, Integer>();
        msg.put("total", 1);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
