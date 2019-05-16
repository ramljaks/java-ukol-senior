package com.etnetera.hr.config;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomUriBuilder {

    public URI getURI(String root, String path, String paramName, Object paramValue) {
        Map<String, String> params = new HashMap<>();

        URI uri = UriComponentsBuilder.fromUriString(root + path)
                .buildAndExpand(params)
                .toUri();
        return UriComponentsBuilder
                .fromUri(uri)
                .queryParam(paramName, paramValue)
                .build()
                .toUri();
    }
}
