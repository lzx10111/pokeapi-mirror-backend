package com.example.pokeapi_mirror.service;

import com.example.pokeapi_mirror.model.entity.FavoriteCount;
import com.example.pokeapi_mirror.model.entity.Pokemon;
import com.example.pokeapi_mirror.model.util.PageResult;
import com.example.pokeapi_mirror.model.util.SearchPokemon;
import com.example.pokeapi_mirror.model.util.SimpleMessage;
import com.example.pokeapi_mirror.repository.FavoriteCountRepository;
import com.example.pokeapi_mirror.repository.FavoriteRepository;
import com.example.pokeapi_mirror.repository.PokemonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.example.pokeapi_mirror.repository.PokemonSpecifications.*;

@Service
public class PokemonService {

    private final RestTemplate restTemplate;
    private final MessageSource messageSource;
    private final PokemonRepository pokemonRepository;
    private final FavoriteRepository favoriteRepository;
    private final FavoriteCountRepository favoriteCountRepository;
    
    @Value("${pokeapi.base-url}")
    private String pokeapi_base_url;
    @Value("${pokeapi.total.url}")
    private String pokeapi_total_url;
    @Value("${pokeapi.total.offline}")
    private String pokeapi_total_offline;

    public PokemonService(RestTemplate restTemplate, MessageSource messageSource, PokemonRepository pokemonRepository,
            FavoriteRepository favoriteRepository, FavoriteCountRepository favoriteCountRepository) {
        this.restTemplate = restTemplate;
        this.messageSource = messageSource;
        this.pokemonRepository = pokemonRepository;
        this.favoriteRepository = favoriteRepository;
        this.favoriteCountRepository = favoriteCountRepository;
    }

    public String getTotalCountPokeAPI() {
        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(pokeapi_total_url, HttpMethod.GET, null, String.class);
        } catch (Exception e) {
            return pokeapi_total_offline;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree;

        try {
            tree = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode node = tree.get("count");

        return String.valueOf(node.intValue());
    }

    public List<Pokemon> getListPokeAPI(Integer end) {
        List<Pokemon> list = new ArrayList<>();

        for (int i=1; i <= end; i++) {
            String urlApi = pokeapi_base_url + i;
            ResponseEntity<Pokemon> responseEntity = restTemplate.exchange(
                    urlApi, HttpMethod.GET, null, Pokemon.class);
            list.add(responseEntity.getBody());
        }

        return list;
    }

    public List<Pokemon> getListPokeAPI(Integer start, Integer end) {
        List<Pokemon> list = new ArrayList<>();

        for (int i=start; i <= end; i++) {
            String urlApi = pokeapi_base_url + i;
            ResponseEntity<Pokemon> responseEntity = restTemplate.exchange(
                    urlApi, HttpMethod.GET, null, Pokemon.class);
            list.add(responseEntity.getBody());
        }

        return list;
    }

    public Pokemon getPokemonByIdPokeAPI(String id) {
        String urlApi = pokeapi_base_url.concat(id);
        ResponseEntity<Pokemon> responseEntity = restTemplate.exchange(
                urlApi, HttpMethod.GET, null, Pokemon.class);

        return responseEntity.getBody();
    }

    public Pokemon getPokemonByNamePokeAPI(String name) {
        String urlApi = pokeapi_base_url.concat(name);
        ResponseEntity<Pokemon> responseEntity = restTemplate.exchange(
                urlApi, HttpMethod.GET, null, Pokemon.class);

        return responseEntity.getBody();
    }
    
    public void savePokemonWithIdPokeAPI(String id) {
        pokemonRepository.save(getPokemonByIdPokeAPI(id));
    }

    public void savePokemonWithNamePokeAPI(String name) {
        pokemonRepository.save(getPokemonByNamePokeAPI(name));
    }

    public Long saveListPokemon(String start, String end) {
        List<Pokemon> listPokemon = getListPokeAPI(Integer.parseInt(start), Integer.parseInt(end));
        List<Pokemon> list = pokemonRepository.saveAll(listPokemon);

        return Long.valueOf(list.size());
    }
    
    public boolean existsByIdPokeAPI(String id) {
    	if (id.isEmpty()) {
    		return false;
    	}
    	
        String urlApi = pokeapi_base_url.concat(id);
        ResponseEntity<?> responseEntity;
        
		try {
			responseEntity = restTemplate.exchange(
			        urlApi, HttpMethod.GET, null, Pokemon.class);
		} catch (HttpStatusCodeException e) {
			return e.getStatusCode().is2xxSuccessful();
		}
        
        return responseEntity.getStatusCode().is2xxSuccessful();
    }
    
    public boolean existsByNamePokeAPI(String name) {
    	if (name.isEmpty()) {
    		return false;
    	}
    	
        String urlApi = pokeapi_base_url.concat(name);
        ResponseEntity<?> responseEntity;
        
		try {
			responseEntity = restTemplate.exchange(
			        urlApi, HttpMethod.GET, null, Pokemon.class);
		} catch (HttpStatusCodeException e) {
			return e.getStatusCode().is2xxSuccessful();
		}
        
        return responseEntity.getStatusCode().is2xxSuccessful();
    }

    public List<Pokemon> getListPokemonAll() {
        return pokemonRepository.findAll();
    }

    public Integer getSizeListPokemon(Iterable<Integer> ids) {
        List<Pokemon> list = pokemonRepository.findAllById(ids);

        return list.size();
    }
    
    public PageResult<Pokemon> getListPokemonPage(String page_number, String page_size) {
    	Pageable pageable = PageRequest.of(Integer.parseInt(page_number) - 1, Integer.parseInt(page_size));
    	Page<Pokemon> page = pokemonRepository.findAll(pageable);
    	
    	return new PageResult<Pokemon>(page);
    }
    
	public PageResult<Pokemon> getListPokemonFilteredPage(SearchPokemon searchPokemon, String page_number, String page_size) {
    	Pageable pageable = PageRequest.of(Integer.parseInt(page_number) - 1, Integer.parseInt(page_size));
        Specification<Pokemon> spec = Specification.unrestricted();
        
        spec = spec.and(nameLike(searchPokemon.getName()));
        spec = spec.and(idLike(searchPokemon.getId()));
        spec = spec.and(heightLike(searchPokemon.getHeight_min(), searchPokemon.getHeight_max()));
        spec = spec.and(weightLike(searchPokemon.getWeight_min(), searchPokemon.getWeight_max()));
        
        Page<Pokemon> page = pokemonRepository.findAll(spec, pageable);

    	return new PageResult<Pokemon>(page);
    }
    
    public Optional<Pokemon> getPokemonByID(Integer id) {

        return pokemonRepository.findById(id);
    }

    public Optional<FavoriteCount> getFavoriteCountByID(Integer id) {
        return favoriteCountRepository.findById(id);
    }
    
    public Long getTotalCountSpecificPokemonFavorite(String id) {
        long total = favoriteRepository.countByPokemonId(Integer.parseInt(id));
        return Long.valueOf(total);
    }

    public String getTotalPokemons() {
        return String.valueOf(getTotalCountPokeAPI());
    }

    public Long deleteListPokemon(String start, String end) {
        Specification<Pokemon> spec = Specification.unrestricted();
        
        spec = spec.and(idGreaterThanOrEqualTo(start));
        spec = spec.and(idLessThanOrEqualTo(end));
        
        long total = pokemonRepository.delete(spec);

        return total;
    }

    public void deletePokemonWithId(String id) {
        pokemonRepository.deleteById(Integer.valueOf(id));
    }

    public void deletePokemonWithName(String name) {
        pokemonRepository.deleteByName(name);
    }
    
    public boolean existsPokemonById(String id) {
    	if (id.isEmpty()) {
    		return false;
    	}
    	
    	return pokemonRepository.existsById(Integer.parseInt(id));
    }
    
    public boolean existsPokemonByName(String name) {
    	if (name.isEmpty()) {
    		return false;
    	}
    	
    	return pokemonRepository.existsByName(name);
    }

    public SimpleMessage findPokemonByIDHasErrors(String id, Locale locale) {
        return new SimpleMessage("id", id, messageSource.getMessage("group.start.required", null, locale));
    }
    
    public List<SimpleMessage> addGroupHasErrors(String groupStart, String groupEnd, String fieldName1, String fieldName2, Locale locale) {
    	List<SimpleMessage> errors = new ArrayList<>();
    	
    	if (!(existsByIdPokeAPI(groupStart))) {
    		errors.add(new SimpleMessage(fieldName1, groupStart, messageSource.getMessage("group.start.required", null, locale)));
        }
    	
    	if (!(existsByIdPokeAPI(groupEnd))) {
    		errors.add(new SimpleMessage(fieldName2, groupEnd, messageSource.getMessage("group.end.required", null, locale)));
        }
    	
    	if (Integer.parseInt(groupStart) >= Integer.parseInt(groupEnd)) {
    		errors.add(new SimpleMessage("group", String.join(", ", groupStart, groupEnd), 
    				messageSource.getMessage("group.range.type", null, locale)));
    	}
    	
    	if (!errors.isEmpty()) {
    		return errors;
    	}
    	
    	String s = "";
    	for (int i=Integer.parseInt(groupStart); i<Integer.parseInt(groupEnd)+1 ; i++) {
    		if (existsPokemonById(String.valueOf(i))) {
    			s += String.valueOf(i) + ", ";
    		}
    	}
    	
    	if (!s.isEmpty()) {
    		errors.add(new SimpleMessage("group", s.substring(0, s.length() - 2), messageSource.getMessage("group.add.conflict", null, locale)));
            return errors;
        }
    	
    	return Collections.emptyList();
    }
    
    
    public List<SimpleMessage> deleteGroupHasErrors(String groupStart, String groupEnd, String fieldName1, String fieldName2, Locale locale) {
    	List<SimpleMessage> errors = new ArrayList<>();
    	
    	if (!existsPokemonById(groupStart)) {
    		errors.add(new SimpleMessage(fieldName1, groupStart, messageSource.getMessage("group.start.required", null, locale)));
        }
    	
    	if (!existsPokemonById(groupEnd)) {
    		errors.add(new SimpleMessage(fieldName2, groupEnd, messageSource.getMessage("group.end.required", null, locale)));
        }
    	
    	if (Integer.parseInt(groupStart) >= Integer.parseInt(groupEnd)) {
    		errors.add(new SimpleMessage("group", String.join(", ", groupStart, groupEnd), 
    				messageSource.getMessage("group.range.type", null, locale)));
    	}

        if (!errors.isEmpty()) {
    		return errors;
    	}
    	
        return Collections.emptyList();
    }
    
    public List<SimpleMessage> addSpecificHasErrors(String id, String name, String fieldName1, String fieldName2, Locale locale) {
    	List<SimpleMessage> errors = new ArrayList<>();

    	if (id.isEmpty() && name.isEmpty()) {
    		errors.add(new SimpleMessage("specific", null, messageSource.getMessage("specific.fields.notempty", null, locale)));
    		return errors;
    	}
    	
    	if (existsPokemonById(id)) {
    		errors.add(new SimpleMessage(fieldName1, id, messageSource.getMessage("specific.fields.conflict", null, locale)));
    	}
    	
    	if (existsPokemonByName(name)) {
    		errors.add(new SimpleMessage(fieldName2, name, messageSource.getMessage("specific.fields.conflict", null, locale)));
    	}
    	
    	if (!errors.isEmpty()) {
    		return errors;
    	}
    	
    	if (!existsByIdPokeAPI(id) && !id.isEmpty()) {
    		errors.add(new SimpleMessage(fieldName1, id, messageSource.getMessage("specific.id.required", null, locale)));
        }
    	
    	if (!existsByNamePokeAPI(name) && !name.isEmpty()) {
    		errors.add(new SimpleMessage(fieldName2, name, messageSource.getMessage("specific.name.required", null, locale)));
        }
    	
    	if (!(id.isEmpty() && name.isEmpty()) && !errors.isEmpty()) {
    		return errors;
    	}
    	
    	Pokemon pokemon = getPokemonByIdPokeAPI(id);
    	if (!pokemon.getName().equalsIgnoreCase(name) && !name.isEmpty()) {
    		errors.add(new SimpleMessage("specific", String.join(", ", id, name), messageSource.getMessage("specific.fields.notfound", null, locale)));
            return errors;
        }
    	
        return Collections.emptyList();
    }
    
    public List<SimpleMessage> deleteSpecificHasErrors(String id, String name, String fieldName1, String fieldName2, Locale locale) {
    	List<SimpleMessage> errors = new ArrayList<>();

    	if (id.isEmpty() && name.isEmpty()) {
    		errors.add(new SimpleMessage("specific", null, messageSource.getMessage("specific.fields.notempty", null, locale)));
    		return errors;
    	}
    	
    	if (!existsPokemonById(id) && !id.isEmpty()) {
    		errors.add(new SimpleMessage(fieldName1, id, messageSource.getMessage("specific.id.notfound", null, locale)));
        }
    	
    	if (!existsPokemonByName(name) && !name.isEmpty()) {
    		errors.add(new SimpleMessage(fieldName2, name, messageSource.getMessage("specific.name.notfound", null, locale)));
        }
    	
    	if (!(id.isEmpty() && name.isEmpty()) && !errors.isEmpty()) {
    		return errors;
    	}
    	
    	Pokemon pokemon = getPokemonByID(Integer.parseInt(id)).get();
    	if (!pokemon.getName().equalsIgnoreCase(name) && !name.isEmpty()) {
    		errors.add(new SimpleMessage("specific", String.join(", ", id, name), messageSource.getMessage("specific.fields.notfound", null, locale)));
            return errors;
        }
    	
        return Collections.emptyList();
    }
}
