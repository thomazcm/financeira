package com.thomazcm.financeira.api.service;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EntryEndpointHelper {

    public BodyBuilder created(String path, Long id) {
        URI uri = UriComponentsBuilder.fromPath(path).buildAndExpand(id).toUri();
        return ResponseEntity.created(uri);
    }
    
}
