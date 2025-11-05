package com.example.pokeapi_mirror;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;

public final class DocumentationFieldDescriptors {
    private DocumentationFieldDescriptors() {
    }

    public static FieldDescriptor[] pokemonWithBeneathPath() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación del Pokémon."),
            fieldWithPath("name").description("El nombre del Pokémon."),
            fieldWithPath("height").description("La altura del Pokémon."),
            fieldWithPath("weight").description("El peso del Pokémon."),
            fieldWithPath("base_experience").description("La experiencia base del Pokémon."),
            subsectionWithPath("abilities").description("Las habilidades del Pokémon."),
            subsectionWithPath("cries").description("Los sonidos únicos del Pokémon."),
            subsectionWithPath("sprites").description("Las imágenes bidimensionales que representan al Pokémon.") };
    }

    public static FieldDescriptor[] pokemonSimplified() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación del Pokémon."),
            fieldWithPath("name").description("El nombre del Pokémon."),
            fieldWithPath("height").description("La altura del Pokémon."),
            fieldWithPath("weight").description("El peso del Pokémon."),
            fieldWithPath("base_experience").description("La experiencia base del Pokémon."),
            fieldWithPath("abilities").description("Este valor se omitirá para simplificar esta parte de la documentación."),
            fieldWithPath("cries").description("Este valor se omitirá para simplificar esta parte de la documentación."),
            fieldWithPath("sprites").description("Este valor se omitirá para simplificar esta parte de la documentación.") };
    }

    public static FieldDescriptor[] abilitiesWithBeneathPath() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación."),
            subsectionWithPath("ability").description("Habilidad especifica del Pokémon."),
            fieldWithPath("is_hidden").description("Específica si la habilidad es oculta o no."),
            fieldWithPath("slot").description("El número de ranura.") };
    }

    public static FieldDescriptor[] ability() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación."),
            fieldWithPath("name").description("El nombre de la habilidad."),
            fieldWithPath("url").description("URL con más información de la habilidad.") };
    }

    public static FieldDescriptor[] cries() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación."),
            fieldWithPath("latest").description("Sonido del Pokémon."),
            fieldWithPath("legacy").description("Sonido del Pokémon.") };
    }

    public static FieldDescriptor[] spritesWithBeneathPath() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación."),
            subsectionWithPath("other").description("Otras imágenes del Pokémon."),
            fieldWithPath("back_default").description("Imagen del Pokémon."),
            fieldWithPath("back_female").description("Imagen del Pokémon."),
            fieldWithPath("back_shiny").description("Imagen del Pokémon."),
            fieldWithPath("back_shiny_female").description("Imagen del Pokémon."),
            fieldWithPath("front_default").description("Imagen del Pokémon."),
            fieldWithPath("front_female").description("Imagen del Pokémon."),
            fieldWithPath("front_shiny").description("Imagen del Pokémon."),
            fieldWithPath("front_shiny_female").description("Imagen del Pokémon.") };
    }

    public static FieldDescriptor[] otherWithBeneathPath() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación."),
            subsectionWithPath("official-artwork").description("Imágenes oficiales del Pokémon.") };
    }

    public static FieldDescriptor[] officialArtwork() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación."),
            fieldWithPath("front_default").description("Imagen oficial del Pokémon."),
            fieldWithPath("front_shiny").description("Imagen oficial del Pokémon.") };
    }

    public static FieldDescriptor[] pageResultWithBeneathPath() {
        return new FieldDescriptor[] {
            subsectionWithPath("content").description("Los Pokémon encontrados."),
            fieldWithPath("totalPages").description("El número total de páginas."),
            fieldWithPath("totalElements").description("El número total de elementos.") };
    }

    public static FieldDescriptor[] totalCount() {
        return new FieldDescriptor[] {
            fieldWithPath("totalCount").description("El número total de usuarios que tienen de favorito al Pokémon en específico.") };
    }

    public static FieldDescriptor[] group() {
        return new FieldDescriptor[] {
            fieldWithPath("start").description("El número de inicio del rango."),
            fieldWithPath("end").description("El número final del rango.") };
    }

    public static FieldDescriptor[] specific() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación del Pokémon."),
            fieldWithPath("name").description("El nombre del Pokémon.") };
    }

    public static FieldDescriptor[] total() {
        return new FieldDescriptor[] {
            fieldWithPath("total").description("El número de total de Pokémon agregados.") };
    }

    public static FieldDescriptor[] id() {
        return new FieldDescriptor[] {
            fieldWithPath("id").description("El número de identificación del Pokémon.") };
    }

    public static FieldDescriptor[] isFavorite() {
        return new FieldDescriptor[] {
            fieldWithPath("isFavorite").description("El número de identificación.") };
    }

    public static FieldDescriptor[] favorite() {
        return new FieldDescriptor[] {
            fieldWithPath("username").description("El nombre del usuario."),
            fieldWithPath("pokemonId").description("El número de identificación del Pokémon.") };
    }
}
