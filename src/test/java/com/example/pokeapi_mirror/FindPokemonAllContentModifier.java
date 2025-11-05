package com.example.pokeapi_mirror;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.ContentModifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FindPokemonAllContentModifier implements ContentModifier {

    @Override
    public byte[] modifyContent(byte[] originalContent, MediaType contentType) {
        String content = new String(originalContent);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = null;

        try {
            arrayNode = (ArrayNode) objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        while (arrayNode.size() > 3) {
            arrayNode.remove(arrayNode.size() - 1);
        }

        for (JsonNode j: arrayNode) {
            ObjectNode objectNode = (ObjectNode) j;
            objectNode.putArray("abilities");
            objectNode.putObject("cries");
            objectNode.putObject("sprites");
        }

        String result = "";

        try {
            result = objectMapper.writeValueAsString(arrayNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result.getBytes();
    }
}
