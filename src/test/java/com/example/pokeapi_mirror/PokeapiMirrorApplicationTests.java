package com.example.pokeapi_mirror;

import com.example.pokeapi_mirror.model.util.PokemonGroup;
import com.example.pokeapi_mirror.model.util.PokemonId;
import com.example.pokeapi_mirror.model.util.PokemonSpecific;
import com.example.pokeapi_mirror.model.util.SearchPokemon;
import com.example.pokeapi_mirror.service.PokemonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.ContentModifyingOperationPreprocessor;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PokeapiMirrorApplicationTests {

	@MockitoBean
	JwtDecoder jwtDecoder;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation).uris()
						.withScheme("http")
						.withHost("localhost")
						.withPort(8090).and().operationPreprocessors()
						.withRequestDefaults(prettyPrint())
						.withResponseDefaults(prettyPrint()))
				.build();
	}

	@Test
	@WithMockJwt(username = "user-test", roles = { "USER" })
	void findPokemonAllTest() throws Exception {
		this.mockMvc.perform(get("/api/pokemon/find/all"))
				.andExpect(status().isOk())
				.andDo(document("find-pokemon-all",
				preprocessResponse(new ContentModifyingOperationPreprocessor(new FindPokemonAllContentModifier()))));
	}

	@Test
	@WithMockJwt(username = "user-test", roles = { "USER" })
	void findPokemonByPageTest() throws Exception {

		this.mockMvc
				.perform(get("/api/pokemon/find?page=1&elements_per_page=3")
						.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("find-pokemon-by-page",
				preprocessResponse(new ContentModifyingOperationPreprocessor(new FindPokemonPageContentModifier())),
				queryParameters(DocumentationParameterDescriptors.pageFind()),
				responseFields(DocumentationFieldDescriptors.pageResultWithBeneathPath()),
				responseFields(beneathPath("content").withSubsectionId("pokemon"),
				DocumentationFieldDescriptors.pokemonSimplified())));
	}

	@Test
	@WithMockJwt(username = "user-test", roles = { "USER" })
	void findPokemonByFilterTest() throws Exception {
		SearchPokemon searchPokemon = new SearchPokemon("", "", "7", "", "", "");
		String query = QueryParametersBuilder.fromObject(searchPokemon);

		this.mockMvc
				.perform(get("/api/pokemon/find/filter?".concat(query).concat("&page=1&elements_per_page=3"))
						.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("find-pokemon-by-filter",
				preprocessResponse(new ContentModifyingOperationPreprocessor(new FindPokemonPageContentModifier())),
				queryParameters(DocumentationParameterDescriptors.joinArrays(DocumentationParameterDescriptors.pageFind(), DocumentationParameterDescriptors.filter())),
				responseFields(DocumentationFieldDescriptors.pageResultWithBeneathPath()),
				responseFields(beneathPath("content").withSubsectionId("pokemon"),
				DocumentationFieldDescriptors.pokemonSimplified())));
	}

	@Test
	@WithMockJwt(username = "user-test", roles = { "USER" })
	void findPokemonByIDTest() throws Exception {

		this.mockMvc
				.perform(get("/api/pokemon/{id}", "1")
						.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("find-pokemon-by-id",
				pathParameters(DocumentationParameterDescriptors.id()),
				responseFields(DocumentationFieldDescriptors.pokemonWithBeneathPath()),
				responseFields(beneathPath("abilities").withSubsectionId("abilities"),
				DocumentationFieldDescriptors.abilitiesWithBeneathPath()),
				responseFields(beneathPath("abilities[].ability").withSubsectionId("ability"),
				DocumentationFieldDescriptors.ability()),
				responseFields(beneathPath("cries").withSubsectionId("cries"),
				DocumentationFieldDescriptors.cries()),
				responseFields(beneathPath("sprites").withSubsectionId("sprites"),
				DocumentationFieldDescriptors.spritesWithBeneathPath()),
				responseFields(beneathPath("sprites.other").withSubsectionId("other"),
				DocumentationFieldDescriptors.otherWithBeneathPath()),
				responseFields(beneathPath("sprites.other.official-artwork").withSubsectionId("official-artwork"),
				DocumentationFieldDescriptors.officialArtwork())));
	}

	@Test
	@WithMockJwt(username = "user-test", roles = { "USER" })
	void totalCountTest() throws Exception {

		this.mockMvc
				.perform(get("/api/pokemon/total_count/{id}", "9")
						.accept(APPLICATION_JSON))
				.andExpectAll(status().isOk(),
						jsonPath("$.totalCount").value("2"))
				.andDo(document("total-count",
				pathParameters(DocumentationParameterDescriptors.id()),
				responseFields(DocumentationFieldDescriptors.totalCount())));
	}

	@Test
	@WithMockJwt(username = "admin-test", roles = { "USER", "ADMIN" })
	void addGroupTest() throws Exception {
		PokemonGroup pokemonGroup = new PokemonGroup("53", "58");

		this.mockMvc
				.perform(post("/api/admin/add_group")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(pokemonGroup)))
				.andExpectAll(status().isOk(),
						jsonPath("$.total").value("6"))
				.andDo(document("add-group",
				requestFields(DocumentationFieldDescriptors.group()),
				responseFields(DocumentationFieldDescriptors.total())));
	}

	@Test
	@WithMockJwt(username = "admin-test", roles = { "USER", "ADMIN" })
	void deleteGroupTest() throws Exception {
		PokemonGroup pokemonGroup = new PokemonGroup("23", "30");

		this.mockMvc
				.perform(post("/api/admin/delete_group")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(pokemonGroup)))
				.andExpectAll(status().isOk(),
						jsonPath("$.total").value("8"))
				.andDo(document("delete-group",
				requestFields(DocumentationFieldDescriptors.group()),
				responseFields(DocumentationFieldDescriptors.total())));
	}

	@Test
	@WithMockJwt(username = "admin-test", roles = { "USER", "ADMIN" })
	void addSpecificTest() throws Exception {
		PokemonSpecific pokemonSpecific = new PokemonSpecific("77", "");

		this.mockMvc
				.perform(post("/api/admin/add_specific")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(pokemonSpecific)))
				.andExpectAll(status().isOk(),
						jsonPath("$.total").value("1"))
				.andDo(document("add-specific",
				requestFields(DocumentationFieldDescriptors.specific()),
				responseFields(DocumentationFieldDescriptors.total())));
	}

	@Test
	@WithMockJwt(username = "admin-test", roles = { "USER", "ADMIN" })
	void deleteSpecificTest() throws Exception {
		PokemonSpecific pokemonSpecific = new PokemonSpecific("21", "");

		this.mockMvc
				.perform(post("/api/admin/delete_specific")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(pokemonSpecific)))
				.andExpectAll(status().isOk(),
						jsonPath("$.total").value("1"))
				.andDo(document("delete-specific",
				requestFields(DocumentationFieldDescriptors.specific()),
				responseFields(DocumentationFieldDescriptors.total())));
	}

	@Test
	@WithMockJwt(username = "user-test", roles = { "USER" })
	void addFavoriteTest() throws Exception {
		PokemonId pokemonId = new PokemonId("14");

		this.mockMvc
				.perform(post("/api/user/add_favorite")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(pokemonId)))
				.andExpectAll(status().isOk(),
						jsonPath("$.isFavorite").isBoolean())
				.andDo(document("add-favorite",
				requestFields(DocumentationFieldDescriptors.id()),
				responseFields(DocumentationFieldDescriptors.isFavorite())));
	}

	@Test
	@WithMockJwt(username = "user-test", roles = { "USER" })
	void findFavoritesTest() throws Exception {

		this.mockMvc
				.perform(get("/api/user/find_favorites")
						.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("find-favorites",
				responseFields(beneathPath("[]").withSubsectionId("favorite"),
				DocumentationFieldDescriptors.favorite())));
	}

	@Test
	@WithMockJwt(username = "user-test", roles = { "USER" })
	void isUserFavoriteTest() throws Exception {

		this.mockMvc
				.perform(get("/api/user/is_user_favorite/{id}", "5")
						.accept(APPLICATION_JSON))
				.andExpectAll(status().isOk(),
						jsonPath("$.isFavorite").value(false))
				.andDo(document("is-user-favorite",
				pathParameters(DocumentationParameterDescriptors.id()),
				responseFields(DocumentationFieldDescriptors.isFavorite())));
	}

	@AfterAll
	static void resetAll(@Autowired PokemonService pokemonService) {
		if (pokemonService.existsPokemonById("53")) {
			pokemonService.deleteListPokemon("53", "58");
		}

		if (!pokemonService.existsPokemonById("23")) {
			pokemonService.saveListPokemon("23", "30");
		}

		if (pokemonService.existsPokemonById("77")) {
			pokemonService.deletePokemonWithId("77");
		}

		if (!pokemonService.existsPokemonById("21")) {
			pokemonService.savePokemonWithIdPokeAPI("21");
		}
	}
}
