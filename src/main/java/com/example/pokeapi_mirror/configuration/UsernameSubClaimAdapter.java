package com.example.pokeapi_mirror.configuration;

import java.util.Map;
import java.util.Collections;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.core.convert.converter.Converter;

public class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>> {
    private final MappedJwtClaimSetConverter delegate =
            MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

    public Map<String, Object> convert(Map<String, Object> claims) {
        Map<String, Object> convertedClaims = this.delegate.convert(claims);

        String username = (String) convertedClaims.get("preferred_username");
        convertedClaims.put("sub", username);

        return convertedClaims;
    }
}
