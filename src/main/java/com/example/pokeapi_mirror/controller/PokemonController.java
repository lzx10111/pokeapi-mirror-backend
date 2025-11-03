package com.example.pokeapi_mirror.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pokeapi_mirror.annotation.IntegerPositive;
import com.example.pokeapi_mirror.model.entity.Pokemon;
import com.example.pokeapi_mirror.model.util.MessageType;
import com.example.pokeapi_mirror.model.util.SearchPokemon;
import com.example.pokeapi_mirror.model.util.SimpleMessage;
import com.example.pokeapi_mirror.service.PokemonService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Validated
@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {
	PokemonService pokemonService;
	
	public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/find/all")
    public ResponseEntity<List<Pokemon>> findPokemonAll(){
 
        return new ResponseEntity<>(pokemonService.getListPokemonAll(), HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/find")
    public ResponseEntity<?> findPokemonByPage(@RequestParam(name = "page", defaultValue = "1") @IntegerPositive(message = "{page.type}")
    	@Size(max = 5, message = "{page.size}") String page, @RequestParam(name = "elements_per_page", defaultValue = "10") @IntegerPositive(message = "{elementsPerPage.type}")
    	@Size(max = 5, message = "{elementsPerPage.size}") String elements_per_page){

        return new ResponseEntity<>(pokemonService.getListPokemonPage(page, elements_per_page), HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/find/filter")
    public ResponseEntity<?> findPokemonByFilter(@Valid SearchPokemon searchPokemon, @RequestParam(name = "page", defaultValue = "1") @IntegerPositive(message = "{page.type}")
    	@Size(max = 5, message = "{page.size}") String page, @RequestParam(name = "elements_per_page", defaultValue = "10") @IntegerPositive(message = "{elementsPerPage.type}")
    	@Size(max = 5, message = "{elementsPerPage.size}") String elements_per_page){

		return new ResponseEntity<>(pokemonService.getListPokemonFilteredPage(searchPokemon, page, elements_per_page), HttpStatus.OK);  
    }
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/{id}")
    public ResponseEntity<?> findPokemonByID(@PathVariable
    	@IntegerPositive(message = "{id.type}")
    	@Size(max = 5, message = "{id.size}") String id){
        
        Optional<Pokemon> pokemon = pokemonService.getPokemonByID(Integer.parseInt(id));
        if (!pokemon.isPresent()) {
        	return new ResponseEntity<>(new SimpleMessage("id", id, "Pokemon no encontrado.", MessageType.ERROR), HttpStatus.NOT_FOUND);
        }
		
		return new ResponseEntity<>(pokemon.get(), HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/total_count/{id}")
    public ResponseEntity<?> totalCount(@PathVariable
    	@IntegerPositive(message = "{idFilter.type}")
    	@Size(max = 10, message = "{idFilter.size}") String id) {

        if (!pokemonService.existsPokemonById(id)) {
        	return new ResponseEntity<>(new SimpleMessage("id", id, "Pokemon no encontrado", MessageType.ERROR), HttpStatus.NOT_FOUND);
        }
        
        HashMap<String, String> msg = new HashMap<String, String>();
		msg.put("totalCount", pokemonService.getTotalCountSpecificPokemonFavorite(id).toString());
		
		return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
