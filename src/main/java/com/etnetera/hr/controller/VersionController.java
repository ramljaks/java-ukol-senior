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
import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping("/version")
public class VersionController {

    private Logger LOG = LoggerFactory.getLogger(Version.class);

    private final VersionRepository repository;

    @Autowired
    public VersionController(VersionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public Iterable<Version> versions() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Version> getVersion(@PathVariable Long id) {
        Optional<Version> version = repository.findById(id);
        return version.map(res -> ResponseEntity.ok().body(res))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Version> addVersion(@RequestBody Version version) {
        LOG.info("Request to create verson: {}", version);
        Version result = repository.save(version);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/edit")
    public ResponseEntity<Version> updateVersion(@Valid @RequestBody Version version) {
        LOG.info("Request to update version: {}", version);
        Version result = repository.save(version);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Version> deleteVersion(@PathVariable Long id) {
        LOG.info("Request to delete version with id: {}", id);
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v")
    public Iterable<Version> getVersionsByVersionName(@RequestParam String v) {
        return repository.findAllByVersion(v);
    }

    @GetMapping("/hypeLevel")
    public Iterable<Version> getVersionsByHypeLevel(@RequestParam String hypeLevel) {
        return repository.findAllByHypeLevel(hypeLevel);
    }

    @GetMapping("/deprecationDate")
    public Iterable<Version> getVersionsByDeprecationDate(@RequestParam Date deprecationDate) {
        return repository.findAllByDeprecationDate(deprecationDate);
    }

    @GetMapping("/javaScriptFramework/id")
    public Iterable<Version> getVersionsByJavaScriptFramework_Id(@RequestParam(name = "id") Long id) {
        return repository.findAllByJavaScriptFramework_Id(id);
    }

    @GetMapping("/javaScriptFramework/name")
    public Iterable<Version> getVersionsByJavaScriptFramework_Name(@RequestParam(name = "name") String name) {
        return repository.findAllByJavaScriptFramework_Name(name);
    }
}
