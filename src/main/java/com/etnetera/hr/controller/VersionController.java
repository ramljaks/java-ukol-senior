package com.etnetera.hr.controller;

import com.etnetera.hr.data.Version;
import com.etnetera.hr.repository.VersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class VersionController {

    private Logger LOG = LoggerFactory.getLogger(Version.class);

    private final VersionRepository repository;

    @Autowired
    public VersionController(VersionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/versions")
    public Iterable<Version> versions() {
        return repository.findAll();
    }

    @GetMapping("/version/{id}")
    public ResponseEntity<Version> getVersion(@PathVariable Long id) {
        Optional<Version> version = repository.findById(id);
        return version.map(res -> ResponseEntity.ok().body(res))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/version/add")
    public ResponseEntity<Version> addVersion(@RequestBody Version version) {
        LOG.info("Request to create verson: {}", version);
        Version result = repository.save(version);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/version/edit")
    public ResponseEntity<Version> updateVersion(@Valid @RequestBody Version version) {
        LOG.info("Request to update version: {}", version);
        Version result = repository.save(version);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/version/{id}")
    public ResponseEntity<Version> deleteVersion(@PathVariable Long id) {
        LOG.info("Request to delete version with id: {}", id);
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
