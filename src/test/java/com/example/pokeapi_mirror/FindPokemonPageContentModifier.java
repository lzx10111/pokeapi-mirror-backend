package com.example.pokeapi_mirror;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.ContentModifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FindPokemonPageContentModifier implements ContentModifier {

    @Override
    public byte[] modifyContent(byte[] originalContent, MediaType contentType) {
        String content = new String(originalContent);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        try {
            jsonNode = objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ArrayNode arrayNode = (ArrayNode) jsonNode.get("content");

        for (JsonNode j: arrayNode) {
            ObjectNode objectNode = (ObjectNode) j;
            objectNode.putArray("abilities");
            objectNode.putObject("cries");
            objectNode.putObject("sprites");
        }

        String result = "";

        try {
            result = objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result.getBytes();
    }
}