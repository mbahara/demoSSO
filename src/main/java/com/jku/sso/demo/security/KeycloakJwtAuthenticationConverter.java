package com.jku.sso.demo.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class KeycloakJwtAuthenticationConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        return new JwtAuthenticationToken(
                source,
                Stream.concat(
                                new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                                extractResourceRoles(source).stream())
                        .collect(toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        var resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));

        var client1 = (Map<String, List<String>>) resourceAccess.get("product-app");
        var client2 = (Map<String, List<String>>) resourceAccess.get("book-app");

        var roles = (ArrayList<String>) client1.get("roles");
        roles.addAll((ArrayList<String>)client2.get("roles"));

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
                .collect(toSet());
    }
}