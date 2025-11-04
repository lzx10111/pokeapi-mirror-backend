package com.example.pokeapi_mirror;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class WithMockJwtSecurityContextFactory implements WithSecurityContextFactory<WithMockJwt> {

    @Override
    public SecurityContext createSecurityContext(WithMockJwt annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Map<String, Object> claims = Map.of(
                "sub", annotation.username(),
                "roles", Arrays.stream(annotation.roles())
                        .map(role -> "ROLE_" + role)
                        .collect(Collectors.joining(" "))
        );

        Jwt jwt = new Jwt("mock-token", Instant.now(), Instant.now().plusSeconds(3600),
                Map.of("alg", "none"), claims);

        Authentication authentication = new JwtAuthenticationToken(jwt,
                Arrays.stream(annotation.roles())
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList()));

        context.setAuthentication(authentication);
        
        return context;
    }
}
