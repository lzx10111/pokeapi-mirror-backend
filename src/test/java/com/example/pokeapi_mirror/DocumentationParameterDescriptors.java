package com.example.pokeapi_mirror;

import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import java.util.Arrays;

public final class DocumentationParameterDescriptors {
    private DocumentationParameterDescriptors() {
    }

    public static ParameterDescriptor[] id() {
        return new ParameterDescriptor[] {
            parameterWithName("id").description("El número de identificación del Pokémon.") };
    }
    
    public static ParameterDescriptor[] pageFind() {
        return new ParameterDescriptor[] {
            parameterWithName("page").description("El número de la página."),
            parameterWithName("elements_per_page").description("El número de elementos por página.") };
    }

    public static ParameterDescriptor[] filter() {
        return new ParameterDescriptor[] {
            parameterWithName("id").description("El número de identificación del Pokémon."),
            parameterWithName("name").description("El nombre del Pokémon."),
            parameterWithName("height_max").description("La altura máxima del Pokémon."),
            parameterWithName("height_min").description("La altura mínima del Pokémon."),
            parameterWithName("weight_max").description("El peso máximo del Pokémon."),
            parameterWithName("weight_min").description("El peso mínimo del Pokémon.") };
    }

    public static ParameterDescriptor[] joinArrays(ParameterDescriptor[] first, ParameterDescriptor[] second) {
        // Source - https://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java
        // Posted by rghome
        // Retrieved 4/11/2025, License - CC-BY-SA 4.0

        ParameterDescriptor[] both = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, both, first.length, second.length);

        return both;
    }
}
